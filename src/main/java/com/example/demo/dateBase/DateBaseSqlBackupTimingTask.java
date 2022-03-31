package com.example.demo.dateBase;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @ClassName DateBaseSqlBackupTimingTask.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月05日 11:04:00
 */
@Configuration
@Component
public class DateBaseSqlBackupTimingTask    {

    //备份的数据库名
//    @Value("${mysqlDataBase.name}")
    private String dbName;


    //用户名
//    @Value("${spring.datasource.username}")
    private String username;


    //密码
//    @Value("${spring.datasource.password}")
    private String password;


    //备份数据库文件地址
//    @Value("${mysqlDataBase.url}")
    private String filePath;


    //ip地址
//    @Value("${mysqlDataBase.ip}")
    private String ip;


}
