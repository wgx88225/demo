package com.example.demo.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.*;

/**
 * @ClassName ThreadPoolConfig.java
 * @Description 线程池配置
 * @Author Vince
 * @CreateTime 2021年09月14日 15:14:00
 */
@Slf4j
@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {

    // 核心线程池大小
    private int corePoolSize = 50;

    // 最大可创建的线程数
    private int maxPoolSize = 200;

    // 队列最大长度
    private int queueCapacity = 1000;

    // 线程池维护线程所允许的空闲时间
    private int keepAliveSeconds = 300;

    @Bean
    @Primary
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolExecutorMdcWrapper();
        executor.setMaxPoolSize(maxPoolSize);
        executor.setCorePoolSize(corePoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix("vince-");
        // 线程池对拒绝任务(无线程可用)的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

//    @Bean
//    @Primary
//    public ThreadPoolExecutor myTaskAsyncPool() {
//         BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(queueCapacity);
//
//        ThreadPoolTaskExecutor executor = new ThreadPoolExecutorMdcWrapper(corePoolSize,maxPoolSize,keepAliveSeconds, TimeUnit.SECONDS,workQueue);
//        //核心线程池大小
////        executor.setCorePoolSize(corePoolSize);
//        //最大线程数
////        executor.setMaxPoolSize(maxPoolSize);
////        //队列容量
////        executor.setQueueCapacity(queueCapacity);
////        //活跃时间
////        executor.setKeepAliveSeconds(keepAliveSeconds);
////        //线程名字前缀
////        executor.setThreadNamePrefix("MyExecutor-");
//
//        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
//        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
////        executor.setWaitForTasksToCompleteOnShutdown(
////                true
////        );
////        executor.setAwaitTerminationSeconds(60);
////
////        executor.initialize();
////        ThreadFactory guavaThreadFactory = new ThreadFactoryBuilder().setNameFormat("MyExecutor-").build();
//        ThreadFactory springThreadFactory = new CustomizableThreadFactory("vince-pool-");
//        executor.setThreadFactory(springThreadFactory);
//        return executor;
//    }
    /**
     * 线程异常处理
     */
    @Bean
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new OrderAsyncUncaughtExceptionHandler();
    }

    /**
     * 线程异常处理类
     */
    static class OrderAsyncUncaughtExceptionHandler implements AsyncUncaughtExceptionHandler {
        @Override
        public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            ex.printStackTrace();
            log.info("异步调用方法[{}]出现异常：{}， 详细异常：{}", method.getName(), ex.getMessage(), ex);
            log.error("异步调用方法[{}]出现异常：{}， 详细异常：{}", method.getName(), ex.getMessage(), ex);
        }
    }
}
