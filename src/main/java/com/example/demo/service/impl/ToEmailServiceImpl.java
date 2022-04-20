package com.example.demo.service.impl;

import com.example.demo.bo.ToEmail;
import com.example.demo.service.ToEmailService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

/**
 * @ClassName ToEmailServiceImpl.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年04月19日 10:34:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ToEmailServiceImpl implements ToEmailService {


    @NonNull
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    @SneakyThrows
    @Override
    public void commonEmail(ToEmail toEmail) {
        //创建简单邮件消息
        SimpleMailMessage message = new SimpleMailMessage();
        //谁发的
        message.setFrom(from);
        //谁要接收
        message.setTo(toEmail.getTos());
        //邮件标题
        message.setSubject(toEmail.getSubject());
        //邮件内容
        message.setText(toEmail.getContent());
        mailSender.send(message);
//        try {
//            mailSender.send(message);
//
//            return JsonReturn.buildSuccess(toEmail.getTos(), "发送普通邮件成功");
//        } catch (MailException e) {
//            e.printStackTrace();
//            return JsonReturn.buildFailure("普通邮件方失败");
//        }
    }

    @Override
    public void htmlEmail(ToEmail toEmail) throws MessagingException {

    }

    @Override
    public void enclosureEmail(ToEmail toEmail, MultipartFile multipartFile) {

    }

    @Override
    public void staticEmail(ToEmail toEmail, MultipartFile multipartFile, String resId) {

    }
}
