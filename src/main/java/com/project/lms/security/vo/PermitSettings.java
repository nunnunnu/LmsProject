package com.project.lms.security.vo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
//임의로 만든거 
@Component
@ConfigurationProperties(prefix = "permission")
@Data
public class PermitSettings {
    String[] permitAllUrls;
}
