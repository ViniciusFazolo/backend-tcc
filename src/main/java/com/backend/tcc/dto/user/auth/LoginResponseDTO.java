package com.backend.tcc.dto.user.auth;

public record LoginResponseDTO(
    String token,
    String loginName,
    String role,
    String id
) {
    
}
