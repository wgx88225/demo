package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.ResultVO;
import com.example.demo.utils.TraceIdUtil;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName TestController.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年07月07日 16:23:00
 */
@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @NonNull
    private final UserService userService;

    @PostMapping("/pushFile")
    public String pushFile(@RequestPart("files") MultipartFile[] files, User user) {
        for (MultipartFile file : files) {
            System.out.println(file.getOriginalFilename() + "---" + file.getSize());
        }

        System.out.println(user);
        return "上传成功！";
    }

    @PostMapping("/postUser")
    public ResponseEntity<ResultVO<User>> postUser(@RequestBody User user) {
        log.info("提交用户信息：{}", JSON.toJSONString(user));
        String jsonString = JSON.toJSONString(user);
//        JSON.toJSONString()
        User user1 = getUser(1L);
        testAsync();
        String traceId = TraceIdUtil.getTraceId();
        System.out.println("traceId:" + traceId);
        return ResultVO.failEntity();
    }

    //threadPoolTaskExecutor
    @Async("myTaskAsyncPool")
    public void testAsync() {
        log.info("执行异步任务开始。。。。");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("当前线程名：" + Thread.currentThread().getName());
        log.info("执行异步任务结束。。。。");
    }

    @GetMapping("/testUser")
    public ResponseEntity<Map<String, Object>> testUser(Integer age, String username) {

        Map<String, Object> res = Maps.newHashMap();
        res.put("age", age);
        res.put("username", username);
        log.info("请求参数：", res);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping("getUser/{id}")
    public User getUser(Long id) {
        log.info("根据ID获取用户信息....");
        return userService.getById(id);
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
