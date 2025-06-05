package com.backend.tcc.dto.user.auth;

public record LoginRequestDTO(
    String login,
    String password
) {
    
}
