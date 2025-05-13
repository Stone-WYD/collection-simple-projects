package com.jgdsun.ba.mybatis.config;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;


@Slf4j
@Aspect
@Component
@Configuration
@ConditionalOnProperty(name = "dblog.error", havingValue = "true")
public class SqlErrorlogInterceptorAspect {

    private static final ThreadLocal<String> sqlHolder = new ThreadLocal<>();

    // todo 如果拦截 Mapper 接口，则会跟 MyBatis-plus 代理 IService 发生冲突。当使用 IService 时，请不要拦截 Mapper 接口。
    //  打印出日志的前提是捕获到异常，代码里请不要在catch块中只打印日志不抛出异常，否则代理执行不到打印sql的位置。
    @Pointcut("execution(* com.jgdsun.ba.*.mapper.*.*(..))")
    public void mapperPointcut() {}

    @AfterReturning("mapperPointcut()")
    public void afterReturning() {
        sqlHolder.remove();
    }

    @AfterThrowing(pointcut = "mapperPointcut()",
            throwing = "ex")
    public void afterThrowing(Throwable ex) {
        log.error("sql执行异常，sql完整内容为：" + sqlHolder.get() + "；抛出的异常为"
                , ex);
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new InnerInterceptor() {
            @Override
            public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) throws SQLException {
                sqlHolder.set(generateExecutableSql(ms, parameter));
                InnerInterceptor.super.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
            }

            @Override
            public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
                sqlHolder.set(generateExecutableSql(ms, parameter));
                InnerInterceptor.super.beforeUpdate(executor, ms, parameter);
            }


        });
        return interceptor;
    }

    private String generateExecutableSql(MappedStatement mappedStatement, Object parameterObject) {
        BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<String> paramValues = new ArrayList<>();
        org.apache.ibatis.session.Configuration configuration = mappedStatement.getConfiguration();
        // 按？顺序处理每个参数的值
        for (ParameterMapping mapping : parameterMappings) {
            String property = mapping.getProperty();
            // 数组的情况特殊处理一下
            int index = 0;
            if (property.startsWith("__frch_")) {
                String[] split = property.split("_");
                String s = split[split.length - 1];
                index = Integer.parseInt(s);
            }
            // 使用 mybatis-plus 的 lambda 生成的 sql 语句会自带 et. 或者 ew. 前缀
            if (property.startsWith("et.") || property.startsWith("ew.")) {
                property = property.substring(3);
            }
            paramValues.add(convertToSqlString(getParameterValue(parameterObject, property, configuration, index)));
        }
        // 替换SQL中的占位符
        String sql = boundSql.getSql();
        return replacePlaceholders(sql, paramValues);
    }

    private Object getParameterValue(Object parameterObject, String property
            , org.apache.ibatis.session.Configuration configuration, Integer index) {
        if (parameterObject == null) {
            return null;
        }

        if (parameterObject instanceof String) {
            return parameterObject;
        }
        if (parameterObject instanceof Number || parameterObject instanceof Boolean) {
            return convertToSqlString(parameterObject);
        }

        // 处理MyBatis包装的Map参数（含param1、_parameter等键）
        if (parameterObject instanceof Map) {
            Map<Object, Object> paramMap = (Map<Object, Object>) parameterObject;
            // 尝试直接获取属性（如原始Map中的键）
            if (paramMap.containsKey(property)) {
                return paramMap.get(property);
            }
            // 尝试从_parameter或param1中解包
            Object unpackedParam = paramMap.getOrDefault("_parameter", null);
            unpackedParam = paramMap.getOrDefault("param1", unpackedParam);
            unpackedParam = paramMap.getOrDefault("et", unpackedParam);
            unpackedParam = paramMap.getOrDefault("ew", unpackedParam);
            if (unpackedParam != null && unpackedParam != parameterObject) {
                return getParameterValue(unpackedParam, property, configuration, index);
            }
        }

        if (parameterObject instanceof List) {
            parameterObject = ((List<?>) parameterObject).get(index);
            // 如果是基本类型，转换成 String 后返回
            if (parameterObject instanceof String) {
                return parameterObject;
            } else if (parameterObject instanceof Number || parameterObject instanceof Boolean) {
                return convertToSqlString(parameterObject);
            }
        }

        if (parameterObject instanceof Set) {
            // todo 效率较低，待优化
            parameterObject = ((Set<?>) parameterObject).toArray()[index];
            // 如果是基本类型，转换成 String 后返回
            if (parameterObject instanceof String) {
                return parameterObject;
            } else if (parameterObject instanceof Number || parameterObject instanceof Boolean) {
                return convertToSqlString(parameterObject);
            }
        }

        // 通过反射获取Bean属性或Map键值
        MetaObject metaObject = MetaObject.forObject(parameterObject, configuration.getObjectFactory(),
                configuration.getObjectWrapperFactory(), configuration.getReflectorFactory());
        return metaObject.getValue(property);
    }

    private String convertToSqlString(Object value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof String) {
            return "'" + ((String) value).replace("'", "''") + "'";
        }
        if (value instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return "'" + sdf.format(value) + "'";
        }
        if (value instanceof Number) {
            return value.toString();
        }
        if (value instanceof Boolean) {
            return (Boolean) value ? "1" : "0";
        }
        return "'" + value.toString().replace("'", "''") + "'";
    }

    private String replacePlaceholders(String sql, List<String> paramValues) {
        StringBuilder sb = new StringBuilder();
        int paramIndex = 0;
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if (c == '?' && paramIndex < paramValues.size()) {
                sb.append(paramValues.get(paramIndex));
                paramIndex++;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

}
