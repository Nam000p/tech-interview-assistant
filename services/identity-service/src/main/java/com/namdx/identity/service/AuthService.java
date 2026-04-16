package com.namdx.identity.service;

import com.namdx.identity.dto.auth.LoginRequest;
import com.namdx.identity.dto.auth.RegistrationRequest;
import com.namdx.identity.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    User register(RegistrationRequest request);

    void login(LoginRequest request, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
}