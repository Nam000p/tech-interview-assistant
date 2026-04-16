package com.namdx.identity.service.impl;

import com.namdx.identity.dto.auth.LoginRequest;
import com.namdx.identity.dto.auth.RegistrationRequest;
import com.namdx.identity.entity.Role;
import com.namdx.identity.entity.User;
import com.namdx.identity.repository.RoleRepository;
import com.namdx.identity.repository.UserRepository;
import com.namdx.identity.service.AuthService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final SecurityContextRepository securityContextRepository;

    @Override
    public User register(RegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalStateException("Email already registered!");
        }
        Set<Role> roles = new HashSet<Role>();
        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            roles.add(roleRepository.findByName("CANDIDATE")
                    .orElseThrow(() -> new EntityNotFoundException("Default role not found!")));
        } else {
            request.getRoles().forEach(name -> {
                roles.add(roleRepository.findByName(name)
                        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + name)));
            });
        }
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .build();
        return userRepository.save(user);
    }

    @Override
    public void login(LoginRequest request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), servletRequest, servletResponse);
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid email or password!");
        }

    }
}