package com.example.demo.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * http 请求返回结果集固定格式
 */

@Data
@NoArgsConstructor
public class ResultVO<T> {

    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public static final int SUCCESS_CODE = HttpStatus.OK.value();

    /**
     * 业务代码，200 为正常，其它为异常
     */

    private int code = SUCCESS_CODE;
    /**
     * 错误信息，业务代码非 200 时才显示
     */


    private String message = SUCCESS;
    /**
     * 业务数据
     */

    private T data;
    private String requestId;

    public ResultVO(int code, String message, T t) {
        this.code = code;
        this.message = message;
        this.data = t;
        this.requestId = TraceIdUtil.getTraceId();
    }

    // = TraceIdUtil.getTraceId()
    public ResultVO(T data) {
        this(SUCCESS_CODE, SUCCESS, (T) data);
    }

    public ResultVO(String msg) {
        this(SUCCESS_CODE, msg, null);
    }

    /**
     * 返回结果集
     */
    public static <T> ResultVO<T> data(int code, String msg, T data) {
        return new ResultVO<>(code, msg, data);
    }

    /**
     * 返回默认的成功结果集
     */
    public static <T> ResultVO success() {
        return success(SUCCESS, "success");
    }

    /**
     * 返回默认的成功结果集
     */
    public static <T> ResultVO<T> success(T data) {
        return success(SUCCESS, data);
    }

    /**
     * 返回带自定义消息的成功结果集
     */
    public static <T> ResultVO<T> success(String msg, T data) {
        return data(SUCCESS_CODE, msg, data);
    }

    /**
     * 返回带自定义 http 状态码和错误信息的结果集
     */
    public static <T> ResultVO<T> fail() {
        return new ResultVO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "fail", null);
    }

    /**
     * 返回带自定义 http 状态码和错误信息的结果集
     */
    public static <T> ResultVO<T> fail(String msg) {
        return new ResultVO<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
    }

    /**
     * 返回带自定义 http 状态码和错误信息的结果集
     */
    public static <T> ResultVO<T> fail(HttpStatus httpStatus, String msg) {
        return new ResultVO<>(httpStatus.value(), msg, null);
    }

    /**
     * 返回带自定义 http 状态码、错误信息、和具体错误信息体的结果集
     */
    public static <T> ResultVO<T> fail(HttpStatus httpStatus, String msg, T t) {
        return new ResultVO<>(httpStatus.value(), msg, t);
    }

    /**
     * 返回带自定义 http 状态码code和错误信息的结果集
     */
    public static <T> ResultVO<T> fail(Integer code, String msg) {
        return new ResultVO<>(code, msg, null);
    }

    public static <T> ResponseEntity<ResultVO<T>> responseEntity(HttpStatus httpStatus, String error, T t) {
        ResultVO<T> result = new ResultVO<>();
        result.code = httpStatus.value();
        if (httpStatus.is2xxSuccessful())
            result.data = t;
        else
            result.message = error;
        return ResponseEntity.status(httpStatus).body(result);
    }

    /**
     * 非 200 情况下使用
     */
    public static <T> ResponseEntity<ResultVO<T>> responseEntityWithError(HttpStatus httpStatus, String error) {
        return responseEntity(httpStatus, error, null);
    }

    /**
     * 200 情况下使用
     */
    public static <T> ResponseEntity<ResultVO<T>> responseEntityWithOk(T t) {
        return responseEntity(HttpStatus.OK, null, t);
    }

    /**
     * 把 ResultVO 封装成 ResponseEntity
     * 以 http 响应状态码控制 ResultVO 的业务码
     */
    public static <T> ResponseEntity<ResultVO<T>> getResponseEntity(ResultVO<T> ResultVO) {
        if (SUCCESS.equals(ResultVO.getMessage())) {
            if (HttpStatus.OK.value() != ResultVO.getCode())
                ResultVO.setCode(HttpStatus.OK.value());
            return ResponseEntity.ok(ResultVO);
        } else {
            ResultVO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResultVO);
        }
    }

    /**
     * 把 ResultVO 转换成 ResponseEntity
     * 以 ResultVO 的业务码控制 http 响应状态码
     */
    public static <T> ResponseEntity<ResultVO<T>> convertToResponseEntity(ResultVO<T> ResultVO) {
        if (ResultVO.code == SUCCESS_CODE)
            return ResponseEntity.ok(ResultVO);
        else
            return ResponseEntity.status(ResultVO.code).body(ResultVO);
    }

    public static <T> ResponseEntity<ResultVO<T>> successEntity() {
        return ResponseEntity.ok(success());
    }

    public static <T> ResponseEntity<ResultVO<T>> successEntity(String msg, T t) {
        return ResponseEntity.ok(success(msg, t));
    }

    public static <T> ResponseEntity<ResultVO<T>> successEntity(T t) {
        return ResponseEntity.ok(success(t));
    }

    public static <T> ResponseEntity<ResultVO<T>> failEntity(HttpStatus httpStatus, String msg) {
        return ResponseEntity.status(httpStatus).body(fail(httpStatus, msg));
    }

    public static <T> ResponseEntity<ResultVO<T>> failEntity(HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(fail(httpStatus, httpStatus.getReasonPhrase()));
    }

    public static <T> ResponseEntity<ResultVO<T>> failEntity(String msg) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
    }

    public static <T> ResponseEntity<ResultVO<T>> failEntity() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "fail"));
    }
}