package com.backend.tcc.dto.group;

import com.backend.tcc.domain.user.User;

public record GroupResponseDTO(
        String id,
        User adm,
        String description,
        String image,
        String image_name,
        String name) {
}
