package com.springboot.training.spaceover.spacecrew.manager.configuration;

import com.springboot.training.spaceover.spacecrew.manager.utils.interceptors.HttpHeaderEnrichmentInterceptor;
import com.springboot.training.spaceover.spacecrew.manager.utils.interceptors.HttpLoggerInterceptor;
import com.springboot.training.spaceover.spacecrew.manager.utils.interceptors.MdcInitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@Profile("test")
class SpaceCrewManagerWebConfigurationTest implements WebMvcConfigurer {

  @Autowired
  private MdcInitInterceptor mdcInitHandler;

  @Autowired
  private HttpHeaderEnrichmentInterceptor headerEnrichmentHandlerInterceptor;

  @Autowired
  private HttpLoggerInterceptor httpLoggerInterceptor;

  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(mdcInitHandler);
    registry.addInterceptor(headerEnrichmentHandlerInterceptor);
    registry.addInterceptor(httpLoggerInterceptor);
  }
}