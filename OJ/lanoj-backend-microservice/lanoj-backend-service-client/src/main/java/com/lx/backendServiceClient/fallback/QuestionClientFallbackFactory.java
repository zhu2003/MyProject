package com.lx.backendServiceClient.fallback;

import com.lx.backendServiceClient.service.QuestionFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

@Slf4j
public class QuestionClientFallbackFactory implements FallbackFactory<QuestionFeignClient> {
    @Override
    public QuestionFeignClient create(Throwable cause) {
        log.error("被限流了");
        return null;
    }
}
