package com.tessaro.springsecurity.security.filterJWTv2.credentials;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
// CLasse que será o objeto base na hoar de fazer login via path /login
public class CredenciaisLogin implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;

    private String password;

}