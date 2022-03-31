package com.example.demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis-plus分页插件
 * @author vince
 * @email wengaoxue@163.com
 * @date 2020-09-26 18:03:49
 */
@Configuration
@EnableTransactionManagement // 开启事务
@MapperScan("com.example.demo.mapper")
public class MybatisPlusConfig {
}
