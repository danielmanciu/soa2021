package com.soa.login.security;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtContext {
    @Value("${jwt.token.expiration.time}")
    private String expiration; // 10 days
    @Value("${jwt.secret.key}")
    private String secret;
    @Value("${jwt.token.prefix}")
    private String tokenPrefix;
    @Value("${jwt.header.string}")
    private String headerString;
    @Value("${login.route}")
    private String route;
}
