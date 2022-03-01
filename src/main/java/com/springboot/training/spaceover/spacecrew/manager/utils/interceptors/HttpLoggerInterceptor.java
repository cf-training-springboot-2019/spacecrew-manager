package com.springboot.training.spaceover.spacecrew.manager.utils.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class HttpLoggerInterceptor implements HandlerInterceptor {

    /**
     * Simple stop watch, allowing for timing of a number of tasks, exposing total running time and running time for each named task.
     * This is not meant to be used in a live production-environment, mainly due to the object is not designed to be thread-safe and does not use synchronization.
     */
    StopWatch stopWatch;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        stopWatch = new StopWatch();
        //LT5.1-Add logging for inbound request processing
        stopWatch.start();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //LT5.2-Add logging for outbound response result
        stopWatch.stop();
        //LT5.3-Add logging for inbound request processing duration
    }

}