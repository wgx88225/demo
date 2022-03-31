package com.example.demo.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName GuavaTest.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月30日 15:45:00
 */
public class GuavaTest {

    public static void main(String[] args) throws ExecutionException {
        LoadingCache<String, BigDecimal> cache = CacheBuilder.newBuilder()
                .maximumSize(10).refreshAfterWrite(10, TimeUnit.MINUTES)
                .softValues() //软引用
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<String, BigDecimal>() {
                    @Override
                    public BigDecimal load(String key) throws Exception {
                        return new BigDecimal(0);
                    }
                });
        BigDecimal bigDecimal = cache.get("1");
        System.out.println(bigDecimal);

    }
}
