package com.example.demo.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.util.UUID;

/**
 * @ClassName TraceIdUtil.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年12月17日 12:33:00
 */
public class TraceIdUtil {

    public static final String TRACE_ID = "traceId";
    /**
     * 当traceId为空时，显示的traceId。随意。
     */
    private static final String DEFAULT_TRACE_ID = "0";

    /**
     * 设置traceId
     */
    public static void setTraceId(String traceId) {
        //如果参数为空，则设置默认traceId
        traceId = StringUtils.isBlank(traceId) ? DEFAULT_TRACE_ID : traceId;
        //将traceId放到MDC中
        MDC.put(TRACE_ID, traceId);
    }

    /**
     * 获取traceId
     */
    public static String getTraceId() {
        //获取
        String traceId = MDC.get(TRACE_ID);
        //如果traceId为空，则返回默认值
        return StringUtils.isBlank(traceId) ? DEFAULT_TRACE_ID : traceId;
    }

    /**
     * 判断traceId为默认值
     */
    public static Boolean defaultTraceId(String traceId) {
        return DEFAULT_TRACE_ID.equals(traceId);
    }

    /**
     * 生成traceId
     */
    public static String genTraceId() {
        return UUID.randomUUID().toString();
    }

}
