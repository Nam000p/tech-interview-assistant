package com.namdx.identity.controller;

import com.namdx.identity.dto.auth.AuthResponse;
import com.namdx.identity.dto.auth.LoginRequest;
import com.namdx.identity.dto.auth.RegistrationRequest;
import com.namdx.identity.dto.user.UserResponse;
import com.namdx.identity.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @MutationMapping
    public UserResponse register(@Valid @Argument RegistrationRequest request) {
        return authService.register(request);
    }

    @MutationMapping
    public AuthResponse login(@Valid @Argument LoginRequest request) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpServletRequest servletRequest = attributes.getRequest();
        HttpServletResponse servletResponse = attributes.getResponse();

        UserResponse user = authService.login(request, servletRequest, servletResponse);

        return AuthResponse.builder()
            .message("Login successful")
            .user(user)
            .build();
    }

    @MutationMapping
    public String logout() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        HttpSession session = attributes.getRequest().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "Logout successful.";
    }
}