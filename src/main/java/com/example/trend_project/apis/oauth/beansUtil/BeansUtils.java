package com.example.trend_project.apis.oauth.beansUtil;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class BeansUtils implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeansUtils.context = applicationContext;
    }

    public static<T> T getBean(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
