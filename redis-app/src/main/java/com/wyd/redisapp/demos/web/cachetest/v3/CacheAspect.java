package com.wyd.redisapp.demos.web.cachetest.v3;

import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.wyd.redisapp.demos.config.RedisClient;
import com.wyd.redisapp.demos.utils.JsonUtil;
import com.wyd.redisapp.demos.web.cachetest.v3.extend.SynchronizeCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.TreeMap;

import static com.wyd.redisapp.demos.web.cachetest.CacheConstant.REDIS_DOUBLE_CACHE;

/**
 * @program: redis-app
 * @author: Stone
 * @create: 2024-05-09 18:32
 * @des: 双重缓存适用场景为查询很多更新很少的情况，这里没有讨论多线程多进程的情况（因为会涉及到很多一致性问题，比较复杂），根据具体业务场景（这种场景需要对一致性有比较高的容忍度或者基本不会出现一致性问题），再去判断是否使用双缓存
 */
@Slf4j
@Component
@Aspect
@ConditionalOnProperty(name = "double.cache.enable", havingValue = "true")
public class CacheAspect {

    @Autowired
    private Cache<String,Object> cache;

    @Autowired
    private SynchronizeCache synchronizeCache;


    @Pointcut("@annotation(com.wyd.redisapp.demos.web.cachetest.v3.DoubleCache)")
    public void cacheAspect(){}

    @Around("cacheAspect()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        // 获取方法参数信息
        String[] parameterNames = signature.getParameterNames();
        Object[] args = point.getArgs();
        TreeMap<String, Object> treeMap = new TreeMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            treeMap.put(parameterNames[i], args[i]);
        }
        // 获取注解上的 elString 内容
        DoubleCache annotation = method.getAnnotation(DoubleCache.class);
        String keyName = annotation.key();
        String keyContent = parse(keyName, treeMap);

        // 获取注解上的其他信息
        // 缓存名，用于隔离不同缓存
        String cacheName = annotation.cacheName();
        // redis 中的 key
        String localKey = cacheName + "_" + keyContent;
        String redisKey = REDIS_DOUBLE_CACHE + localKey;
        // redis 缓存超时时间
        long timeOut = annotation.l2TimeOut();
        // 缓存类型
        CacheType type = annotation.type();

        if (type == CacheType.PUT) {
            // 缓存强制更新
            Object result = point.proceed();
            cache.put(localKey, result);
            RedisClient.setStrWithExpire(redisKey, JsonUtil.toStr(result), timeOut * 1000);
            // 如果是分布式项目，要通知其他节点更新本地缓存
            synchronizeCache.produceMessage(localKey);
            return result;
        }
        if (type == CacheType.DELETE) {
            // 删除缓存
            Object result = point.proceed();
            cache.invalidate(localKey);
            RedisClient.del(redisKey);
            // 2024/5/9 如果是分布式项目，要通知其他节点删除本地缓存
            synchronizeCache.produceMessage(localKey);
            return result;
        }

        // 读写，在哪查到就更新向上的缓存，并返回结果
        Object localResult = cache.getIfPresent(localKey);
        if (Objects.nonNull(localResult)) return localResult;
        String redisResult = RedisClient.getStr(redisKey);
        if (StrUtil.isNotBlank(redisResult)) {
            RedisClient.expire(redisKey, timeOut * 1000);
            Object result = JsonUtil.toObj(redisResult, method.getReturnType());
            cache.put(localKey, result);
            // 如果是分布式项目，此时其实可以不考虑同步更新其他节点的本地缓存
            // 因为一般查询走的就是这部分，访问得最多
            return result;
        }
        Object result = point.proceed();
        cache.put(localKey, result);
        RedisClient.setStrWithExpire(redisKey, JsonUtil.toStr(result), timeOut * 1000);
        return result;
    }

    public static String parse(String elString, TreeMap<String, Object> map) {
        elString = String.format("#{%s}", elString);
        // 创建解析表达式
        ExpressionParser parser = new SpelExpressionParser();
        // 通过 evaluationContext.setVariable 可以在上下文中设定变量
        EvaluationContext context = new StandardEvaluationContext();
        map.forEach(context::setVariable);
        // 解析表达式
        Expression expression = parser.parseExpression(elString, new TemplateParserContext());
        return expression.getValue(context, String.class);
    }

    /*public static void main(String[] args) {
        String elString="#order";
        String elString2="#user.name";
        String elString3="#p0";

        TreeMap<String,Object> map=new TreeMap<>();
        User user = new User();
        user.setName("小明");
        map.put("order","12222");
        map.put("user",user);

        String val = parse(elString, map);
        String val2 = parse(elString2, map);
        String val3 = parse(elString3, map);

        System.out.println(val);
        System.out.println(val2);
        System.out.println(val3);
    }*/
}