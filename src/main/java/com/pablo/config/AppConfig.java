package com.pablo.config;

import com.pablo.interfaces.MessageProvider;
import com.pablo.interfaces.MessageRenderer;
import com.pablo.services.MessageProviderImpl;
import com.pablo.services.MessageRendererImpl;
import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:app.properties")
public class AppConfig {
    @Bean
    @Scope(value = "singleton")
    public MessageProvider messageProviderBean() {
        return new MessageProviderImpl();
    }

    @Bean(initMethod = "render")
    public MessageRenderer messageRendererBean() {
        return new MessageRendererImpl();
    }
}
