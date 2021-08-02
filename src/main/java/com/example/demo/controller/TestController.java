package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

/**
 * @ClassName TestController.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年07月07日 16:23:00
 */
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @NonNull
    private final UserService userService;

    @PostMapping("/pushFile")
    public String pushFile( @RequestPart("files") MultipartFile[] files ,User user) {
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename() +"---"+file.getSize());
        }

        System.out.println(user);
        return "上传成功！";
    }

    @GetMapping("getUser")
    public User getUser() {
//        User user = User.builder().uid(1000L).name("张三").birthDay(new Date()).age(20).build();
        return userService.getUser();
    }

    @GetMapping("set")
    public Set<String> getUser(@RequestParam("orgIdSet") Set<String> orgIdSet) {
        System.out.println(orgIdSet);
        return orgIdSet;
    }

    @GetMapping("retryable")
    /**
     * 失败重试3次
     */
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
    public void registerUidToken(Integer num) {
        System.out.println("执行方法 : " + LocalDateTime.now());

        if (num % 2 == 0) {
            throw new RuntimeException("失败");
        }
    }

    /**
     * 重试失败抛异常上层处理
     *
     * @param e
     */
    @Recover
    public void handleSendMessage(Exception e) {
        System.out.println("执行补偿方法 : " + LocalDateTime.now());
        throw new RuntimeException("注册uid推送token重试失败:" + e.getMessage());

    }


}
