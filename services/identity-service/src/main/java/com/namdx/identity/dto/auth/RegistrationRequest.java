package com.namdx.identity.dto.auth;

import lombok.Data;

import java.util.Set;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}