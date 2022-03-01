package com.springboot.training.spaceover.spacecrew.manager.utils.interceptors;

import com.springboot.training.spaceover.spacecrew.manager.utils.annotatations.ServiceOperation;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

import static com.springboot.training.spaceover.spacecrew.manager.utils.constants.SpaceCrewManagerConstant.*;

@Component
public class MdcInitInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //LT6.1- Include trace-id MDC entry
        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Optional<ServiceOperation> serviceOperationAnnotation = Optional
                    .ofNullable(handlerMethod.getMethodAnnotation(ServiceOperation.class));
            //LT6.2- Include operation MDC entry
        }
        return true;
    }

}
