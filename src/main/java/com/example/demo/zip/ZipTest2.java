package com.example.demo.zip;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName ZipTest2.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月18日 09:30:00
 */
public class ZipTest2 {

    static ThreadPoolTaskExecutor executor;

    static {
        executor.setMaxPoolSize(200);
        executor.setCorePoolSize(50);
        executor.setQueueCapacity(1000);
        executor.setKeepAliveSeconds(300);
        executor.setThreadNamePrefix("Vince-");
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }
    public static void main(String[] args) {
        System.out.println(executor);
    }
}
