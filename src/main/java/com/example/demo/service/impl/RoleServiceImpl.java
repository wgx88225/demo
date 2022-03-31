package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.Role;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.service.RoleService;
import org.springframework.stereotype.Service;

/**
 * @ClassName RoleServiceImpl.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月26日 11:18:00
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
