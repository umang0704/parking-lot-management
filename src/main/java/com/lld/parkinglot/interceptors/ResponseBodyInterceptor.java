package com.lld.parkinglot.interceptors;

import com.lld.parkinglot.constants.LoggingConstants;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseBodyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.remove(LoggingConstants.REQUEST_ID);
        MDC.remove(LoggingConstants.RESPONSE_ID);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
