package com.data2.opendoc.manager.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@Slf4j
@Data
public class DocConfig {

    @Value("${officialEmail}")
    private List<String> officialEmail;
}
