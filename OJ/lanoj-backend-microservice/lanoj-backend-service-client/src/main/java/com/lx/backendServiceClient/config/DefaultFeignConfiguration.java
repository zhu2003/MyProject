package com.lx.backendServiceClient.config;

import com.lx.backendServiceClient.fallback.QuestionClientFallbackFactory;
import org.springframework.context.annotation.Bean;


public class DefaultFeignConfiguration {
    @Bean
    public QuestionClientFallbackFactory globalClientFallbackFactory(){
        return new QuestionClientFallbackFactory();
    }
}
