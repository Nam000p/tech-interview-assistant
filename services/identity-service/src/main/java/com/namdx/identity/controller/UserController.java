package com.namdx.identity.controller;

import com.namdx.identity.dto.user.UserResponse;
import com.namdx.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public UserResponse getMyProfile() {
        return userService.getMyProfile();
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Page<UserResponse> getAllUsers(@Argument int page, @Argument int size) {
        return userService.getAllUsers(PageRequest.of(page, size));
    }
}