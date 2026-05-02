package com.namdx.identity.mapper;

import com.namdx.identity.dto.user.UserResponse;
import com.namdx.identity.entity.Permission;
import com.namdx.identity.entity.User;
import com.namdx.identity.security.UserPrincipal;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserResponse mapToResponse(User user) {
        if (user == null) {
            return null;
        }
        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
        Set<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getPermissionId)
                .collect(Collectors.toSet());
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .roles(roles)
                .permissions(permissions)
                .createdAt(user.getCreatedAt())
                .build();
    }

    public UserResponse mapFromPrincipal(UserPrincipal principal) {
        if (principal == null) {
            return null;
        }
        return UserResponse.builder()
                .id(principal.id())
                .email(principal.email())
                .fullName(principal.fullName())
                .roles(principal.roles())
                .permissions(principal.permissions())
                .build();
    }
}