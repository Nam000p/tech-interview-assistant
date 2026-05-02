package com.namdx.identity.dto.auth;

import com.namdx.identity.dto.user.UserResponse;
import lombok.Builder;

@Builder
public record AuthResponse(
    String message,
    UserResponse user
) {}
