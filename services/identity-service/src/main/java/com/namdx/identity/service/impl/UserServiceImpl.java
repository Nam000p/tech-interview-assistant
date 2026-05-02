package com.namdx.identity.service.impl;

import com.namdx.identity.dto.user.UserResponse;
import com.namdx.identity.entity.User;
import com.namdx.identity.mapper.UserMapper;
import com.namdx.identity.repository.UserRepository;
import com.namdx.identity.security.CustomUserDetails;
import com.namdx.identity.security.UserPrincipal;
import com.namdx.identity.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User is not authenticated");
        }

        UserPrincipal principal = ((CustomUserDetails) authentication.getPrincipal()).userPrincipal();
        return userMapper.mapFromPrincipal(principal);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::mapToResponse);
    }
}