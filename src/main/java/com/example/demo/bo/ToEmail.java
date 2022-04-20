package com.example.demo.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName ToEmail.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年04月19日 10:33:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToEmail implements Serializable {

    /**
     * 邮件接收方，可多人
     */
    private String[] tos;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件内容
     */
    private String content;

}
