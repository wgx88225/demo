package com.example.demo.Interceptor;

import com.example.demo.utils.TraceIdUtil;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LogInterceptor.java
 * @Description
 * @Author Vince
 * @CreateTime 2021年12月17日 12:37:00
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果有上层调用就用上层的ID
        String traceId = request.getHeader(TraceIdUtil.TRACE_ID);
        //如果当前traceId为空或者为默认traceId，则生成新的traceId
        if (StringUtils.isBlank(traceId) || TraceIdUtil.defaultTraceId(traceId)){
            traceId = TraceIdUtil.genTraceId();
        }
        //设置traceId
        TraceIdUtil.setTraceId(traceId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //调用结束后删除
        log.info("调用结束后删除...");
        MDC.remove(TraceIdUtil.TRACE_ID);
    }
}
