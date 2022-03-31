package com.example.demo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @ClassName BaseEntity.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年09月14日 11:19:00
 */
@Data

public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建者
     */

    @JsonProperty("create_by")
    @TableField(value = "create_by")
    protected String createBy = "0";
    /**
     * 更新者
     */

    @JsonProperty("update_by")
    @TableField(value = "update_by")
    protected String updateB = "0";

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("update_time")
    private LocalDateTime updateTime;


}
