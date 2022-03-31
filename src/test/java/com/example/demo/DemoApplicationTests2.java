package com.example.demo;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
@Slf4j
class DemoApplicationTests2 {


    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    volatile  Integer num = 1;

    @Test
    void test001() throws Exception {

        threadTest();
//        Thread.sleep(100);
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            log.info("\n======线程id---线程名:{}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
            num = null;
        }, threadPoolTaskExecutor);
    }

    @SneakyThrows
    private void threadTest() {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            log.info("\n======线程id---线程名:{}-{}", Thread.currentThread().getId(), Thread.currentThread().getName());
            String dest = "D:\\ziptest";
            if(num == null) throw new NullPointerException();
            try {
                FileUtils.copyFileToDirectory(new File("E:\\download\\会议服务器_20211125103506105150.zip"),new File(dest));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }, threadPoolTaskExecutor);
        future1.get();
        System.out.println("执行结束");
    }
}