package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.TopicManage;
import com.example.demo.service.TopicManageService;
import com.example.demo.utils.ResultVO;
import com.example.demo.vo.TopicManageVO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName TopicManageController.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年12月31日 15:35:00
 */
@Slf4j
@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicManageController {


    @NonNull
    private final TopicManageService topicManageService;

    @PostMapping()
    public ResponseEntity<ResultVO<Boolean>> save(@RequestBody TopicManageVO topicManageVO) {
        log.info("提交题目信息：{}", JSON.toJSONString(topicManageVO));
        boolean res = topicManageService.add(topicManageVO);
        return ResultVO.successEntity(res);
    }

    @PutMapping()
    public ResponseEntity<ResultVO<Boolean>> edit(@RequestBody TopicManageVO topicManageVO) {
        log.info("提交题目信息：{}", JSON.toJSONString(topicManageVO));
        TopicManage topicManage = new TopicManage();
        BeanUtils.copyProperties(topicManageVO, topicManage);
        boolean update = topicManageService.saveOrUpdate(topicManage);
        return ResultVO.successEntity(update);
    }

    @PutMapping("/err/{topic_id}")
    public ResponseEntity<ResultVO<Boolean>> edit(@PathVariable("topic_id") Long topicId) {
        log.info("提交题目信息：topicId:{}", topicId);
        TopicManage topicManage = topicManageService.getById(topicId);
        topicManage.setErrorNum(topicManage.getErrorNum() + 1);
        boolean update = topicManageService.saveOrUpdate(topicManage);
        return ResultVO.successEntity(update);
    }
}
