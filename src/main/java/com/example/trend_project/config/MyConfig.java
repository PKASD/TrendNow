package com.example.trend_project.config;

import com.example.trend_project.interceptor.LoginInterceptor;
import com.example.trend_project.interceptor.TypoInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class MyConfig implements WebMvcConfigurer {
    private final TypoInterceptor typoInterceptor;
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(typoInterceptor).addPathPatterns("/guest-result");
        registry.addInterceptor(loginInterceptor).addPathPatterns("/search","/result","/gallery/**");
    }

}
