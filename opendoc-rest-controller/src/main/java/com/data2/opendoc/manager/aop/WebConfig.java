package com.data2.opendoc.manager.aop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/opendoc/**")
                .allowedOrigins("http://localhost:8081","http://127.0.0.1:8081", "http://example.com") // 允许的源
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 允许的HTTP方法
                .allowedHeaders("*") // 允许的HTTP头
                .allowCredentials(true); // 是否允许发送Cookie
    }
}
