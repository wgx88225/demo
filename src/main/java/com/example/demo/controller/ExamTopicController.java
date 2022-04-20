package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.entity.ExamTopic;
import com.example.demo.service.ExamTopicService;
import com.example.demo.utils.ResultVO;
import com.example.demo.validator.AddGroup;
import com.example.demo.validator.UpdateGroup;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


/**
 * @ClassName ExamTopicController.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年04月11日 10:16:00
 */
@Slf4j
@RestController
@RequestMapping("/examTopic")
@RequiredArgsConstructor
public class ExamTopicController {

    @NonNull
    private final ExamTopicService examTopicService;

    @GetMapping("/list")
    public ResponseEntity<ResultVO<List<ExamTopic>>> list() {
        log.info("查询题目列表：");
        return ResultVO.successEntity(examTopicService.list());
    }

    @PostMapping()
    public ResponseEntity<ResultVO<Boolean>> save(@RequestBody @Validated(value = {AddGroup.class}) ExamTopic examTopic) {
        log.info("新增提交题目信息：{}", JSON.toJSONString(examTopic));
        boolean res = examTopicService.save(examTopic);
        return ResultVO.successEntity(res);
    }

    @PutMapping()
    public ResponseEntity<ResultVO<Boolean>> edit(@RequestBody @Validated(value = {UpdateGroup.class}) ExamTopic examTopic) {
        log.info("更新提交题目信息：{}", JSON.toJSONString(examTopic));
        boolean update = examTopicService.updateById(examTopic);
        return ResultVO.successEntity(update);
    }

    @PutMapping("/error_num")
//    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResultVO<Boolean>> updateErrorNum(@RequestBody @Validated(value = {UpdateGroup.class}) ExamTopic exam) {
        Optional<ExamTopic> examTopic = Optional.of(exam);
//        if (!examTopic.isPresent()) throw new BusinessException("更新对象不能为空！");
//        if (exam.getId() == null) throw new BusinessException(ErrorConstant.PARAM_NO_EMPTY, "ID");
//        if (exam.getErrorNum() == null || exam.getErrorNum() == 0)
//            throw new BusinessException("错误次数不能为空");
        log.info("更新提交题目信息：{}", JSON.toJSONString(exam));
        ExamTopic byId = examTopicService.getById(exam.getId());
        byId.setErrorNum(byId.getErrorNum() + (exam.getErrorNum() == null ? 0 : exam.getErrorNum()));
        boolean update = examTopicService.updateById(byId);
//        int i = 1/0;
        return ResultVO.successEntity(update);
    }

}
