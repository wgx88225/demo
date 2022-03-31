package com.example.demo.guava;

import com.google.common.cache.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName StudyGuavaCache.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年12月30日 09:23:00
 */
public class StudyGuavaCache {
    private static Cache<String, String> cache = CacheBuilder.newBuilder()
            .maximumSize(3)
            .build();

    public static void main(String[] args) throws Exception {
        // 设置最大存储
//        setMaximumSize();
        // 设置过期时间
//        setExpirationTime();
        // 弱引用
//        weakValues();
        // 批量清除list中全部key对应的记录
//        batchClean();
        // 移除监听器
//        removalListener();
        // 自动加载
//        automaticLoading();
        // 统计信息
//        stats();
        // LoadingCache
        loadingCache();

    }

//class CacheLoader2<String, String> extends CacheLoader<String, String>{
//
//    @Override
//    public String load(String key) throws Exception {
//        Thread.sleep(1000); //休眠1s，模拟加载数据
//        System.out.println(key + " is loaded from a cacheLoader!");
//        return key + "'s value";
//    }
//}
    private static void loadingCache() throws ExecutionException {

        CacheLoader<String, String> loader = new CacheLoader<String, String> () {
            public String load(String key) throws Exception {
                Thread.sleep(1000); //休眠1s，模拟加载数据
                System.out.println(key + " is loaded from a cacheLoader!");
                return key + "'s value";
            }
        };

            LoadingCache<String,String> loadingCache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .build(loader);//在构建时指定自动加载器

        System.out.println(loadingCache.get("key1"));
        System.out.println(loadingCache.get("key1"));
        System.out.println(loadingCache.get("key2"));
        System.out.println(loadingCache.get("key3"));



    }

    // 统计信息
    private static void stats() {
        Cache<String,String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .recordStats() //开启统计信息开关
                .build();
        cache.put("key1","value1");
        cache.put("key2","value2");
        cache.put("key3","value3");
        cache.put("key4","value4");

        cache.getIfPresent("key1");
        cache.getIfPresent("key2");
        cache.getIfPresent("key3");
        cache.getIfPresent("key4");
        cache.getIfPresent("key5");
        cache.getIfPresent("key6");

        System.out.println(cache.stats()); //获取统计信息
    }

    //自动加载
    private static void automaticLoading() throws Exception {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("thread1");
                try {
                    String value = cache.get("key", new Callable<String>() {
                        public String call() throws Exception {
                            System.out.println("load1"); //加载数据线程执行标志
                            Thread.sleep(1000); //模拟加载时间
                            return "auto load by Callable";
                        }
                    });
                    System.out.println("thread1 " + value);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                System.out.println("thread2");
                try {
                    String value = cache.get("key", new Callable<String>() {
                        public String call() throws Exception {
                            System.out.println("load2"); //加载数据线程执行标志
                            Thread.sleep(1000); //模拟加载时间
                            return "auto load by Callable";
                        }
                    });
                    System.out.println("thread2 " + value);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // 移除监听器
    private static void removalListener() {
        RemovalListener<String, String> listener = new RemovalListener<String, String>() {
            public void onRemoval(RemovalNotification<String, String> notification) {
                System.out.println("[" + notification.getKey() + ":" + notification.getValue() + "] is removed!");
            }
        };
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(3)
                .removalListener(listener) // 移除监听器
                .build();
        Object value = new Object();
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.put("key4", "value4");
        cache.put("key5", "value5");
        cache.put("key6", "value6");
        cache.put("key7", "value7");
        cache.put("key8", "value8");

    }

    // 批量清除list中全部key对应的记录
    private static void batchClean() {
        Cache<String, String> cache = CacheBuilder.newBuilder().build();
        Object value = new Object();
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        List<String> list = new ArrayList<String>();
        list.add("key1");
        list.add("key2");

        cache.invalidateAll(list);//批量清除list中全部key对应的记录
        System.out.println(cache.getIfPresent("key1"));
        System.out.println(cache.getIfPresent("key2"));
        System.out.println(cache.getIfPresent("key3"));

    }

    // 弱引用
    private static void weakValues() {
        Cache<String, Object> cache = CacheBuilder.newBuilder()
                .maximumSize(2)
                .weakValues()
                .build();
        Object value = new Object();
        cache.put("key1", value);

        value = new Object();//原对象不再有强引用
        System.gc();
        System.out.println(cache.getIfPresent("key1"));

    }

    // 设置过期时间
    private static void setExpirationTime() throws Exception {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(2) // 设置最大存储
//                .expireAfterWrite(3, TimeUnit.SECONDS) // 设置过期时间expireAfterWrite方法指定对象被写入到缓存后多久过期
                .expireAfterAccess(3, TimeUnit.SECONDS) // 设置过期时间expireAfterAccess指定对象多久没有被访问后过期。
                .build();
        cache.put("key1", "value1");
        int time = 1;
//        while(true) {
//            System.out.println("第" + time++ + "次取到key1的值为：" + cache.getIfPresent("key1"));
//            Thread.sleep(1000);
//        }
        while (true) {
            Thread.sleep(time * 1000);
            System.out.println("睡眠" + (time++) + "秒后取到key1的值为：" + cache.getIfPresent("key1"));
        }

    }

    // 设置最大存储
    private static void setMaximumSize() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(2) // 设置最大存储
                .build();
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        System.out.println("第一个值：" + cache.getIfPresent("key1"));
        System.out.println("第二个值：" + cache.getIfPresent("key2"));
        System.out.println("第三个值：" + cache.getIfPresent("key3"));

    }
}
