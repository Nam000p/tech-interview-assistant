package com.namdx.identity.security;

import com.namdx.common.security.UserPrincipal;
import com.namdx.identity.entity.Permission;
import com.namdx.identity.entity.User;
import com.namdx.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        Set<String> roles = user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());

        Set<String> permissions = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(Permission::getPermissionId)
                .collect(Collectors.toSet());

        return UserPrincipal.builder()
                .id(user.getId().toString())
                .email(user.getEmail())
                .password(user.getPassword())
                .fullName(user.getFullName())
                .roles(roles)
                .permissions(permissions)
                .build();
    }
}