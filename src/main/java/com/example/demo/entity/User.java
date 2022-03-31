package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 *
 * @author vince
 * @email wengaoxue@163.com
 * @date 2020-09-26 18:03:49
 */
@Data
@TableName("tb_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 指定主键生成策略使用雪花算法（默认策略）
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码，加密存储
     */
    private String password;
    /**
     * 注册手机号
     */
    private String phone;
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
     * 删除标识
     */
    private Integer delFlag;

    @TableField(exist = false)
    private Boolean ok;

}
