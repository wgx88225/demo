package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年07月26日 17:59:00
 */
@Service
public class UserServiceImpl implements UserService {


    @Override
    public User getUser() {
        User user = new User();
        user.setUid(100L);
        user.setName("dsd");
        user.setAge(20);
        return user;
    }
}
