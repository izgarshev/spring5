package com.pablo.services;

import com.pablo.interfaces.MessageProvider;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class MessageProviderImpl implements MessageProvider, BeanNameAware {
    private String beanName;
    @Value("${provider.name}")
    private String name;

    public String getName() {
        return name;
    }


    @Override
    public void setBeanName(String name) {
        beanName = name;
        System.out.println("bean name is: " + name);
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("pre destroy " + beanName);
    }
}
