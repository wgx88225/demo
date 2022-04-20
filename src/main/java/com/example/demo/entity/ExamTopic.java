package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.demo.validator.AddGroup;
import com.example.demo.validator.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName ExamTopic.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年04月11日 10:05:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("exam_topic")
public class ExamTopic implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    /**
     * 主键
     */
    @NotNull(message = "修改必须指定价格模板id", groups = {UpdateGroup.class}) //修改时校验规则
    @Null(message = "新增不能指定价格模板id", groups = {AddGroup.class}) //新增时的校验规则
    private Long id;

    /**
     * 题目
     */
    @NotNull(message = "题目不能为空！", groups = {AddGroup.class}) //修改时校验规则
    private String topic;

    /**
     * 题目类型（0-其他，1-高项，2-架构）
     */
    private Integer type;

    /**
     * 助记
     */
    private String mnemonic;

    /**
     * 参考答案
     */
    private String referAnswer;

    /**
     * 删除标志（-1--删除 1--正常）
     */
    private Integer delFlag;

    /**
     * 创建日期时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 备注
     */
    private String remark;

    /**
     * 错误次数
     */
    @NotNull(message = "错误次数不能为空", groups = {UpdateGroup.class}) //修改时校验规则
    private Integer errorNum;


}
