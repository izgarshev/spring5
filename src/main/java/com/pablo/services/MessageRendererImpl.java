package com.pablo.services;

import com.pablo.interfaces.MessageProvider;
import com.pablo.interfaces.MessageRenderer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class MessageRendererImpl implements MessageRenderer, ApplicationContextAware {
    MessageProvider messageProvider;

    @Autowired
    public void setMessageProvider(MessageProvider messageProvider) {
        this.messageProvider = messageProvider;
    }

    public void render() {
        System.out.println("init method");
        System.out.println(messageProvider.getName());
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("post construct");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("pre destroy");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ((AbstractApplicationContext) applicationContext).registerShutdownHook();
    }
}
