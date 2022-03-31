package com.example.demo.config;

import com.example.demo.Interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @ClassName WebMvcConfig.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年10月18日 17:49:00
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:D://uploadPath/upload");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(new LogInterceptor());
    }
}
