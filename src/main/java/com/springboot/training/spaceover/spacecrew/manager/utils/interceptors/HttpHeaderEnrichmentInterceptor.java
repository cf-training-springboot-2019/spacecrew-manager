package com.springboot.training.spaceover.spacecrew.manager.utils.interceptors;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.*;

@Component
public class HttpHeaderEnrichmentInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        response.addHeader(TRACE_ID_HEADER, MDC.get(TRACE_ID));
        response.addHeader(SERVICE_OPERATION_HEADER, MDC.get(SERVICE_OPERATION));
        return true;
    }

}
