package com.example.demo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.TopicManage;
import com.example.demo.mapper.TopicManageMapper;
import com.example.demo.service.TopicManageService;
import com.example.demo.vo.QuestionAnswerVO;
import com.example.demo.vo.TopicManageVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 题目管理 服务实现类
 * </p>
 *
 * @author vince
 * @since 2021-09-27
 */
@RequiredArgsConstructor
@Slf4j(topic = "businessLog")
@Service
public class TopicManageServiceImpl extends ServiceImpl<TopicManageMapper, TopicManage> implements TopicManageService {


    @Override
    public boolean add(TopicManageVO topicManageVO) {
        TopicManage topicManage = new TopicManage();
        BeanUtils.copyProperties(topicManageVO, topicManage);
        List<QuestionAnswerVO> optionItem = topicManageVO.getOptionItem();
        Assert.isTrue(!ObjectUtils.isEmpty(optionItem), "title.option.cannot.be.blank");
        Assert.isTrue(optionItem.size() >= 2, "topic.no.less.than.two.options");
        topicManage.setOptions(JSON.toJSONString(optionItem));
        // 校验正确答案 是否已经设置
        boolean correctOption = optionItem.stream().anyMatch(item -> item.getCorrect());
        Assert.isTrue(correctOption, "there.is.no.correct.answer");
        //设置正确答案
        String collect = optionItem.stream()
                .filter(QuestionAnswerVO::getCorrect)
                .map(QuestionAnswerVO::getSerialNumber)
                .map(item -> item + "")
                .collect(Collectors.joining(","));
        topicManage.setCorrectOption(collect);
        Assert.isTrue(StringUtils.isNotEmpty(collect), "there.is.no.correct.answer");

        return  saveOrUpdate(topicManage);
    }
}
