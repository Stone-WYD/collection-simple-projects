package com.wyd.satokendemospringboot.demos.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.wyd.satokendemospringboot.demos.common.result.MyResult;
import com.wyd.satokendemospringboot.demos.common.result.MyResultUtil;
import com.wyd.satokendemospringboot.demos.dao.MyUserDao;
import com.wyd.satokendemospringboot.demos.entity.MyUser;
import com.wyd.satokendemospringboot.demos.entity.dto.CacheDTO;
import com.wyd.satokendemospringboot.demos.service.CacheTestService;
import com.wyd.satokendemospringboot.demos.util.SpringUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class CacheTestServiceImpl implements CacheTestService {

    @Resource
    private MyUserDao myUserDao;

    private LoadingCache<String, String> cache;

    @PostConstruct
    public void init(){
        // 缓存的初始化经常放在service的初始化时
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(5, TimeUnit.SECONDS)
                .recordStats(); // 开启统计功能
        // 构建cache对象
        cache = cacheBuilder.build(new CacheLoader<String, String>(){

            @Override
            public String load(String key) throws Exception {
                // 线程休眠模拟生成value需要 1 s
                Thread.sleep(1000);
                return "guava缓存" + key ;
            }
        });
    }


    /*
    * 存在缓存时取出，不存在则重新获取
    * */
    @Cacheable(cacheNames = "user_", key = "#userId")
    @Override
    public CacheDTO queryUserUseCaffineCache(Long userId) {
        MyUser myUser = myUserDao.queryById(userId);
        return new CacheDTO(myUser.getUserName(), myUser.getUserPassword(), new Date());
    }

    @Override
    @CacheEvict(cacheNames = "user_", key = "#userId")
    public CacheDTO testQueryAfterDeleteCache(Long userId) {
        CacheDTO cacheDTO = SpringUtil.getBean(CacheTestService.class).queryUserUseCaffineCache(userId);
        cacheDTO.setName( "删除缓存后再次加入的名字：" + cacheDTO.getName() );
        return cacheDTO;
    }

    /*
    * 不管缓存是否存在，都会重新刷新缓存
    * */
    @CachePut(cacheNames = "user_", key = "#userId")
    @Override
    public CacheDTO udpateUserUseCaffineCache(Long userId) {
        return this.queryUserUseCaffineCache(userId);
    }


    /*
    * 使缓存失效
    * */
    @CacheEvict(cacheNames = "user_", key = "#userId", condition = "#userId == 1L")
    @Override
    public String removeUserCaffineCache(Long userId) {
        if (userId==1L){
            return "单纯地让 userId==1 的缓存失效了...";
        }else return "无事发生";

    }

    /*
    * 多个缓存注解的组合使用
    * */
    @Caching(put = @CachePut(cacheNames = "user_", key = "'1'"),
            evict = @CacheEvict(cacheNames = "user_", key = "'2'"))
    @Override
    public CacheDTO cachingTest() {
        return this.queryUserUseCaffineCache(1L);
    }

    @Override
    public MyResult<MyUser> queryUserWydUseGuavaCache() {
        return null;
    }

    @Override
    public MyResult<String> queryDataByGuava(String key){
        MyResult<String> result = new MyResult<>();
        cache.put("wyd", "wyd固定放入的缓存");
        try {
            result.setData(cache.get(key));
        } catch (ExecutionException e) {
            // 测试不做处理
            e.printStackTrace();
        }
        return MyResultUtil.getTrueResult(result);
    }
}
