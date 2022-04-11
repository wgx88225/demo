package com.example.demo.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @ClassName ErrorConstant.java
 * @Description
 * @Author Vince
 * @CreateTime 2022年04月11日 10:37:00
 */
@Getter
@AllArgsConstructor
public enum ErrorConstant {


    INTERNAL_SERVER_ERROR(10001, "系统内部错误"),

    ADD_EXCEPTION(10002, "新增失败！"),

    UPDATE_EXCEPTION(10003, "更新失败！"),

    DELETE_EXCEPTION(10004, "删除失败！"),

    PARAM_NO_EMPTY(10100, "%s请求参数不能为空！");
    /**
     * 错误码
     */
    private final int code;

    /**
     * 提示信息
     */
    private final String errMsg;

}
