package com.demo.service.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@RefreshScope
@Component
@Data
@ConfigurationProperties(prefix = "demo")
public class DemoConfig {
 
    private String test;
}