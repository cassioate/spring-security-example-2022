package com.tessaro.springsecurity.config.filterJWT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CredenciaisDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;

    private String password;

}