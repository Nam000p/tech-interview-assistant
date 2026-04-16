package com.namdx.identity.controller;

import com.namdx.identity.dto.auth.LoginRequest;
import com.namdx.identity.dto.auth.RegistrationRequest;
import com.namdx.identity.entity.User;
import com.namdx.identity.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequest request) {
        User response = authService.register(request);
        return new ResponseEntity<User>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request,
                                        HttpServletRequest servletRequest,
                                        HttpServletResponse servletResponse) {
        authService.login(request, servletRequest, servletResponse);
        return new ResponseEntity<String>("Login successful. Session created in Redis.", HttpStatus.OK);
    }

    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<String>("Logout successful.", HttpStatus.OK);
    }
}