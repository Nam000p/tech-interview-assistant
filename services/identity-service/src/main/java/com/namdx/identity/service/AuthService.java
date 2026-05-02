package com.namdx.identity.service;

import com.namdx.identity.dto.auth.LoginRequest;
import com.namdx.identity.dto.auth.RegistrationRequest;
import com.namdx.identity.dto.user.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
    UserResponse register(RegistrationRequest request);

    UserResponse login(LoginRequest request, HttpServletRequest servletRequest, HttpServletResponse servletResponse);
}