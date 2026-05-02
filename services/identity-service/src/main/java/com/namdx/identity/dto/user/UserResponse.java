package com.namdx.identity.dto.user;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Builder
public record UserResponse(
    UUID id,
    String email,
    String fullName,
    Set<String> roles,
    Set<String> permissions,
    OffsetDateTime createdAt
) {}