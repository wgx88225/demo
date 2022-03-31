package com.example.demo.config;

import com.example.demo.utils.ThreadMdcUtil;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @ClassName ThreadPoolExecutorMdcWrapper.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年12月17日 13:24:00
 */
public class ThreadPoolExecutorMdcWrapper extends ThreadPoolTaskExecutor {



//    public ThreadPoolExecutorMdcWrapper(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
//        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
//
//    }

    @Override
    public void execute(Runnable task) {
        super.execute(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));

    }

//    @Override
//    public <T> Future<T> submit(Runnable task, T result) {
//        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()), result);
//    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(ThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }


}
