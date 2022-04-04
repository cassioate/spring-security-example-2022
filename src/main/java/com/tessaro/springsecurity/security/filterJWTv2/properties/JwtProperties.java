package com.tessaro.springsecurity.security.filterJWTv2.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
// Classe utilitara para ser criado o secret e o expiration via aplication.properties
public class JwtProperties {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

}
