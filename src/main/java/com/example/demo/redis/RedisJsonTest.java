package com.example.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

/**
 * @ClassName RedisJsonTest.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年12月22日 09:56:00
 */
@Component
public class RedisJsonTest {

    @Autowired
    public RedisTemplate redisTemplate;

    public void testRedisJson(){

    }

}
