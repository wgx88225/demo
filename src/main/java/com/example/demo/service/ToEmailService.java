package com.example.demo.service;

import com.example.demo.bo.ToEmail;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

/**
 * @ClassName ToEmailService.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年04月19日 10:32:00
 */
public interface ToEmailService {
    /**
     * @param toEmail 发送对象
     * @return 统一返回ajax
     * @desc 发送普通邮件 （无其他资源 无html 无附件）
     */
    void commonEmail(ToEmail toEmail);

    /**
     * @param toEmail 发送对象
     * @return 统一返回ajax
     * @Desc 发送html形式的邮件
     */
    void htmlEmail(ToEmail toEmail) throws MessagingException;

    /**
     * 带附件 邮件发送
     */
    void enclosureEmail(ToEmail toEmail, MultipartFile multipartFile);

    /**
     * 一同发送静态资源 图片等
     *
     * @param resId 每个资源对饮给一个Id
     */
    void staticEmail(ToEmail toEmail, MultipartFile multipartFile, String resId);
}
