package com.backend.tcc.dto.user.auth;


public record RegisterUserDTO(
    String name,
    String login,
    String password,
    String role
) {}
