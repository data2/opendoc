package com.data2.opendoc.manager.aop.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public FilterRegistrationBean<JwtFilter> filterRegistrationBean() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/*"); // 拦截所有请求

        return registrationBean;
    }
}
