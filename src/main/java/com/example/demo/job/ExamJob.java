package com.example.demo.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.demo.bo.ToEmail;
import com.example.demo.entity.ExamTopic;
import com.example.demo.service.ExamTopicService;
import com.example.demo.service.ToEmailService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName ExamJob.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年04月19日 10:46:00
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ExamJob {

    @NonNull
    private final ExamTopicService examTopicService;
    @NonNull
    private final ToEmailService toEmailService;

    /**
     * @param
     * @return void
     * @description 定时发题目到邮件
     * @author Vince
     * @version V1.0.0
     * @createTime 2022/4/19 10:49
     */
    @Scheduled(cron = "0 40 08 1/1 * ? ")
    public void sendMail() {
        ToEmail toEmail = new ToEmail();
        toEmail.setTos(new String[]{"442872071@qq.com"});
        toEmail.setSubject("每日案例问答题");

        List<ExamTopic> examTopicList = examTopicService.list(new LambdaQueryWrapper<ExamTopic>().eq(ExamTopic::getType, "1"));
        Collections.shuffle(examTopicList); // 打乱顺序
        List<ExamTopic> list = examTopicList.stream().limit(5).collect(Collectors.toList()); // 限制数量

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            ExamTopic examTopic = list.get(i);
            // 获取题目
            String topic = examTopic.getTopic();
            builder.append(i + 1);
            builder.append(" .");
            builder.append(topic);
            builder.append("[");
            builder.append(examTopic.getId());
            builder.append("]");
            builder.append("\r\n");
            builder.append("\r\n");
            builder.append(" ");
        }
        toEmail.setContent(builder.toString());
        try {
            toEmailService.commonEmail(toEmail);//发送
            log.info("测试邮件已发送。");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("发送邮件时发生异常了！");
        }
    }

}
