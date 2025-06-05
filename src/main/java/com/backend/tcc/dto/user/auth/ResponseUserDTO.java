package com.backend.tcc.dto.user.auth;

import com.backend.tcc.domain.user.UserRole;

public record ResponseUserDTO(
    String id,
    String name,
    String login,
    UserRole roleId
) {} 
