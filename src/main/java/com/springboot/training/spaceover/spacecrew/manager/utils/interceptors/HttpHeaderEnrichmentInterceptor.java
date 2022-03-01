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
        //LT6.3- Include missing X-Trace-Id response header
        //LT6.4- Include missing Service-Operation response header
        return true;
    }

}
