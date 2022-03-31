package com.example.demo.config;

import com.github.benmanes.caffeine.cache.CacheWriter;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.benmanes.caffeine.cache.RemovalCause;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName LoadingCacheConfig.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年12月30日 11:23:00
 */
@Configuration
public class LoadingCacheConfig {

    //定义缓存,可直接使用
    @Bean
    public LoadingCache expiryCache(){
        LoadingCache<String, Object> loadingCache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000)
                //缓存写入/删除监控
                .writer(new CacheWriter<Object, Object>() {
                    @Override
                    public void write(Object key, Object value) { //此方法是同步阻塞的
                        System.out.println("--缓存写入--:key=" + key + ", value=" + value);
                    }
                    @Override
                    public void delete(Object key, Object value, RemovalCause cause) { System.out.println("--缓存删除--:key=" + key); }
                })
                .expireAfterAccess(1, TimeUnit.MINUTES) //过期时间
                .build((String key)->"刷新的数据"); //cacheload实现类,刷新时候调用
        loadingCache.put("name","侯征");
        return loadingCache;
    }
}
