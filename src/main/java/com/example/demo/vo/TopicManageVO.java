package com.example.demo.vo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName TopicManageVO.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年09月27日 15:39:00
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("res_topic_manage")
public class TopicManageVO extends BaseEntity implements Serializable {

    /**
     * 题目管理主键ID
     */
    @JsonProperty(value = "topic_id")
    private Long topicId;
    /**
     * 所属题库ID
     */
    @JsonProperty("que_set_id")
    private Long queSetId;
    /**
     * 所有选项A,B,C,D等
     */
    @JsonProperty("option_item")
    private List<QuestionAnswerVO> optionItem;

    /**
     * 题库名称
     */
    @JsonProperty("que_set_title")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String queSetTitle;

    /**
     * 题目类型(0--其他(默认),1--单选，2--多选)
     */
    @JsonProperty("topic_type")
    private Integer topicType;
    /**
     * 题目描述
     */
    @JsonProperty("topic_desc")
    private String topicDesc;
    /**
     * 所有选项A,B,C,D等
     */
    @JsonIgnore
    private String options;
    /**
     * 正确选项
     */
    @JsonProperty("correct_option")
    @JsonIgnore
    private String correctOption;
    /**
     * 上传图片
     */
    private String img;

    /**
     * 上传图片-源文件名
     */
    private String imgOrgFilename;
    /**
     * 删除标志（-1--删除 1--正常）
     */
//    @JsonProperty("del_flag")
    @TableLogic
    @JsonIgnore
    private Integer delFlag;
    /**
     * 备注
     */
    private String remark;

    /**错误次数*/
    @JsonProperty("error_num")
    private Integer errorNum;
}
