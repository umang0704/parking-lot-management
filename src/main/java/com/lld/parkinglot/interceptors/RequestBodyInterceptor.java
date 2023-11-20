package com.lld.parkinglot.interceptors;

import com.lld.parkinglot.constants.LoggingConstants;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.UUID;
@ControllerAdvice
public class RequestBodyInterceptor extends RequestBodyAdviceAdapter {
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        String requestId = MDC.get(LoggingConstants.REQUEST_ID);
        String responseId = MDC.get(LoggingConstants.RESPONSE_ID);
        if(Objects.isNull(requestId) || requestId.isBlank()) MDC.put(LoggingConstants.REQUEST_ID, UUID.randomUUID().toString());
        if(Objects.isNull(responseId) ||responseId.isBlank()) MDC.put(LoggingConstants.RESPONSE_ID, UUID.randomUUID().toString());

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
