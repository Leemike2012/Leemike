package com.the.electricdoor.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class PathInterceptorConfig implements WebMvcConfigurer{

    @Autowired
    TokenInterceptor tokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //忽略的路径
        String[] excludePathPatterns = {
                "/login"
        };

        registry.addInterceptor(tokenInterceptor).excludePathPatterns(excludePathPatterns);
    }
}
