package com.backend.tcc.dto.user;

import org.springframework.web.multipart.MultipartFile;


public record UserRequestDTO(
        String id,
        String name,
        String login,
        String password,
        MultipartFile image,
        String roleId) {
}
