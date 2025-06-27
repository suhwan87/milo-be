package com.example.milo_be.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private static final String SECURITY_SCHEME_NAME = "BearerAuth";

    // Swagger 문서 설정 (JWT 인증 포함)
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP) // HTTP 인증 방식
                                        .scheme("bearer")               // bearer 방식 명시
                                        .bearerFormat("JWT")            // JWT 포맷
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME)) // 보안 요구사항 추가
                .info(apiInfo()); // API 정보 추가
    }

    // API 기본 정보 설정
    private Info apiInfo() {
        return new Info()
                .title("Milo API 명세서")
                .description("Milo API 명세서를 사용한 Swagger UI")
                .version("1.0.0");
    }
}
