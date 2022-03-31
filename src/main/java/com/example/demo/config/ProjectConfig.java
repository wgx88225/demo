package com.example.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ClassName ProjectConfig.java
 * @Description 读取项目相关配置
 * @Author Vince
 * @CreateTime 2021年09月14日 14:50:00
 */
@Data
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectConfig implements Serializable {
    /**
     * 项目名称
     */
    private String name;

    /**
     * 版本
     */
    private String version;

    /**
     * 版权年份
     */
    private String copyrightYear;


    /**
     * 上传路径
     */
    private String profile;
    private String captchaType;
    private Integer captchaExpiration;
    private Boolean addressEnabled;

}
