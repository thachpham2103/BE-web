package com.example.be.web.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "admin")
public class AdminInfoProperties {

    private String password;
    private String username;
    private String fullName;
    private String email;
}
