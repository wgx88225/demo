package com.example.demo.exception;

import com.example.demo.constant.ErrorConstant;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * @ClassName BusinessException.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年04月11日 10:35:00
 */
public class BusinessException extends RuntimeException {

    @Getter
    private Integer code;
    @Getter
    private String msg;

    /**
     * 使用已有的错误类型
     *
     * @param type 枚举类中的错误类型
     */
    public BusinessException(ErrorConstant type) {

        this.msg = type.getErrMsg();
        this.code = type.getCode();
    }

    public BusinessException(String msg) {

        this.msg = msg;
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public BusinessException(Integer code) {
        this.code = code;
    }

    /**
     * 自定义错误类型
     *
     * @param code 自定义的错误码
     * @param msg  自定义的错误提示
     */
    public BusinessException(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    /**
     * 自定义错误类型
     *
     * @param type 枚举类中的错误类型
     * @param args 可输入提示内容
     */
    public BusinessException(ErrorConstant type, String... args) {
        this.msg = String.format(type.getErrMsg(), args);
        this.code = type.getCode();
    }

}
