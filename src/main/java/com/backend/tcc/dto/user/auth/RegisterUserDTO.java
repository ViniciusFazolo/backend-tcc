package com.backend.tcc.dto.user.auth;

import org.springframework.web.multipart.MultipartFile;

public record RegisterUserDTO(
    String name,
    String login,
    MultipartFile image,
    String password,
    String role
) {}
