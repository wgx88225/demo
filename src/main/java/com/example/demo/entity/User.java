package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * @ClassName User.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年07月07日 16:24:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    private Long uid;
    private String name;
    private Integer age;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date birthDay;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate day;
}
