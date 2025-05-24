package com.backend.tcc.dto.user;

import com.backend.tcc.domain.user.UserRole;

public record UserResponseDTO(
    String id,
    String name,
    String login,
    String image,
    UserRole role
) {
    
}
