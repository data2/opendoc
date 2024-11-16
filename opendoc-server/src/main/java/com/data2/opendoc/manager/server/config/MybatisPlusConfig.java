package com.data2.opendoc.manager.server.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.data2.opendoc.manager.server.mapper")
public class MybatisPlusConfig {
}
