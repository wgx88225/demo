package com.example.demo.controller;

import com.example.demo.config.ProjectConfig;
import com.example.demo.utils.ResultVO;
import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @ClassName TestController.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年07月07日 16:23:00
 */
@Slf4j
@RestController
@RequestMapping("/demo")
@RequiredArgsConstructor
public class DemoController {

    @NonNull
    private final ThreadPoolTaskExecutor threadPool;
    @NonNull
    private final ProjectConfig projectConfig;


    //    @Autowired
//    private ThreadPoolExecutor myTaskAsyncPool;
    @GetMapping("/testProject")
    public ResponseEntity<ResultVO<String>> testProjectConfig() {
        System.out.println(projectConfig);

        return ResultVO.successEntity("成功");
    }

    @GetMapping("/getUser")
    public ResponseEntity<ResultVO<Map<String, Object>>> getUser() {
        Map<String, Object> res = Maps.newHashMap();
        res.put("age", 18);
        res.put("username", "张三");

        CompletableFuture.runAsync(() -> {
            System.out.println("当前线程名：" + Thread.currentThread().getName());
            log.info("demo user.....");
        }, threadPool);
        CompletableFuture.runAsync(() -> {
            System.out.println("当前线程名：" + Thread.currentThread().getName());
            log.info("demo user2.....");
        }, threadPool);
        return ResultVO.successEntity(res);
    }

    @GetMapping("/testUser")
    public ResponseEntity<Map<String, Object>> testUser() {

        Map<String, Object> res = Maps.newHashMap();
        res.put("age", 18);
        res.put("username", "张三");
        log.info("线程名：{}，请求参数1：{}", Thread.currentThread().getName(), res);
        testAsync(res);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=================");
        log.info("线程名：{}，处理请求参数2：{}", Thread.currentThread().getName(), res);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @Async("threadPool")
    public void testAsync(Map<String, Object> res) {
        CompletableFuture.runAsync(() -> {
            log.info("线程名：{}，异步处理请求参数3：{}", Thread.currentThread().getName(), res);
        }, threadPool);
    }

}
