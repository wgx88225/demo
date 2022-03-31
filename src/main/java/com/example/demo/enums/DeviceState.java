package com.example.demo.enums;

import lombok.AllArgsConstructor;

/**
 * @ClassName DeviceState.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年11月04日 13:46:00
 */
@AllArgsConstructor
public enum DeviceState {
    OTHER(-2, "其他"),
    EXPIRED(-1, "已过期"),
    NOT_ACTIVE(0, "未激活"),
    ACTIVE(1, "已激活");
    private Integer value;
    private String msg;
}
