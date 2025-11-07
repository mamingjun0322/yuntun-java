package com.tsuki.yuntun.java.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/Knife4j API文档配置
 */
@Configuration
public class SwaggerConfig {
    
    /**
     * 小程序端API文档
     */
    @Bean
    public GroupedOpenApi appApi() {
        return GroupedOpenApi.builder()
                .group("1-小程序端")
                .pathsToMatch("/api/**")
                .build();
    }
    
    /**
     * 后台管理端API文档
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("2-后台管理端")
                .pathsToMatch("/admin/**")
                .build();
    }
    
    /**
     * API信息
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("云吞点餐系统API文档")
                        .version("1.0.0")
                        .description("基于Spring Boot 3.4.0的点餐系统后端接口文档")
                        .contact(new Contact()
                                .name("Tsuki")
                                .email("your-email@example.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}

