package com.namdx.identity.security;

import lombok.Builder;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Builder
public record UserPrincipal(
        UUID id,
        String email,
        String fullName,
        Set<String> roles,
        Set<String> permissions
) implements Serializable {}