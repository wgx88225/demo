package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 题目管理
 * </p>
 *
 * @author vince
 * @since 2021-09-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("res_topic_manage")
public class TopicManage implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 题目管理主键ID
     */
    @TableId(value = "topic_id", type = IdType.AUTO)
    private Long topicId;
    /**
     * 所属题库ID
     */
    private Long queSetId;
    /**
     * 题目类型(0--其他(默认),1--单选，2--多选)
     */
    private Integer topicType;
    /**
     * 题目描述
     */
    private String topicDesc;
    /**
     * 所有选项A,B,C,D等
     */
    private String options;
    /**
     * 正确选项
     */
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
    @TableLogic
    private Integer delFlag;
    /**
     * 备注
     */
    private String remark;

    /**
     * 创建者
     */
    @JsonProperty("create_by")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    protected String createBy = "0";
    /**
     * 更新者
     */
    @JsonProperty("update_by")
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    protected String updateBy = "0";

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
    /**
     * 错误次数
     */

    private Integer errorNum;


}
