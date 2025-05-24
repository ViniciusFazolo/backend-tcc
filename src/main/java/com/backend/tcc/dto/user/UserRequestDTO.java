package com.backend.tcc.dto.user;

import org.springframework.web.multipart.MultipartFile;

import com.backend.tcc.domain.user.UserRole;

public record UserRequestDTO(
        String id,
        String name,
        String login,
        MultipartFile image,
        UserRole role) {

}
