package com.soa.login.config;

import lombok.Data;

import java.io.Serializable;

@Data
public class AuthenticationRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String password;
}
