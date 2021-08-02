package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry(proxyTargetClass = true)//启用重试机制

public class DemoApplication {

    public static void main(String[] args)  {
        SpringApplication.run(DemoApplication.class, args);
        System.out.println("启动完成。。。。");

    }


}
