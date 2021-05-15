package com.springboot.training.spaceover.spacecrew.manager.configuration;

import com.springboot.training.spaceover.spacecrew.manager.utils.interceptors.HttpHeaderEnrichmentInterceptor;
import com.springboot.training.spaceover.spacecrew.manager.utils.interceptors.MdcInitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class SpaceCrewManagerWebConfiguration {

    @Bean
    public WebMvcConfigurer webMvcConfigurationInitializer(MdcInitInterceptor mdcInitHandler, HttpHeaderEnrichmentInterceptor headerEnrichmentHandlerInterceptor) {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(mdcInitHandler);
                registry.addInterceptor(headerEnrichmentHandlerInterceptor);
            }
        };
    }

    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }

}