package com.example.demo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @ClassName QuestionAnswerVO.java
 * @Description 题目答案实体
 * @Author Vince
 * @CreateTime 2021年09月27日 18:12:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QuestionAnswerVO {
    /**
     * 选项序号
     */
    @JsonProperty("serial_number")
    private Integer serialNumber;
    /**
     * 选项内容
     */
    private String option;
    /**
     * 选项答案
     */

    private Boolean correct = false;

}
