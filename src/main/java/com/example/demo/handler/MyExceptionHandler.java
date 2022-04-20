package com.example.demo.handler;

import com.example.demo.exception.BusinessException;
import com.example.demo.utils.ResultVO;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @ClassName MyExceptionHandler.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年04月11日 10:51:00
 */
@RestControllerAdvice
@Slf4j
public class MyExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO handleAdTocException(BusinessException ex) {
        ex.printStackTrace();
        log.error("出现自定义异常ERROR：", ex);
        return ResultVO.fail(ex.getCode(), ex.getMsg());
    }
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultVO handleConstraintViolationException(Exception e) {
        e.printStackTrace();
        log.error("请求参数校验异常ERROR==>：", e);
        List<ObjectError> allErrors = Lists.newArrayList();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            allErrors = ex.getBindingResult().getAllErrors();

        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            allErrors = ex.getAllErrors();
        }
        if (!ObjectUtils.isEmpty(allErrors)) {
            return ResultVO.fail(HttpStatus.BAD_REQUEST, allErrors.get(0).getDefaultMessage());
        }
        return ResultVO.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultVO handle(Exception ex) {
        ex.printStackTrace();
        log.error("系统内部出现异常ERROR==>：", ex);
        // 参数为空的检验
        return ResultVO.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "系统内部错误!");
    }
}
