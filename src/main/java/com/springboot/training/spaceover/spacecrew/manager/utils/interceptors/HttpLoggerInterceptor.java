package com.springboot.training.spaceover.spacecrew.manager.utils.interceptors;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.LOGGING_HANDLER_INBOUND_MSG;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.LOGGING_HANDLER_OUTBOUND_MSG;
import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.LOGGING_HANDLER_PROCESS_TIME_MSG;

import java.time.Instant;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
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
        log.info(LOGGING_HANDLER_INBOUND_MSG, request.getMethod(), request.getRequestURI(),
                Instant.now());
        stopWatch.start();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info(LOGGING_HANDLER_OUTBOUND_MSG, response.getStatus(), Instant.now());
        stopWatch.stop();
        log.info(LOGGING_HANDLER_PROCESS_TIME_MSG, stopWatch.getTotalTimeMillis());
    }

}