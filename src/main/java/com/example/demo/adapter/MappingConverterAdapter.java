package com.example.demo.adapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @ClassName MappingConverterAdapter.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年07月26日 17:24:00
 */
@Configuration
public class MappingConverterAdapter {
    @Bean
    public Converter<String, LocalDate> DateConvert() {
        return new Converter<String, LocalDate>() {
            @Override
            public LocalDate convert(String source) {
                return LocalDate.parse(source, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            }
        };
    }

}
