package com.example.milo_be.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // 설정 클래스
public class RestTemplateConfig {

    @Bean // RestTemplate 빈 등록 (외부 API 호출용)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}