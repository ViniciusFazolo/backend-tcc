package com.backend.tcc.dto.user.auth;

public record LoginResponseDTO(
    String token,
    String login,
    String role,
    String id
) {
    
}
