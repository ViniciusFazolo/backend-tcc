package com.backend.tcc.dto.user.auth;

import com.backend.tcc.domain.user.UserRole;

public record LoginResponseDTO(
    String token,
    String name,
    UserRole role
) {
    
}
