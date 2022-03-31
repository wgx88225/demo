package com.example.demo.task;

import org.springframework.stereotype.Component;

/**
 * @ClassName TestTask.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月15日 16:06:00
 */
@Component
public class TestTask {

    //    @Scheduled(cron = "0/5 * * * * ?")
    public void syncCouponWaitStatusTask() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
