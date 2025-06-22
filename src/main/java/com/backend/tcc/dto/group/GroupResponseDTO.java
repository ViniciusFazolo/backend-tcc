package com.backend.tcc.dto.group;

import com.backend.tcc.domain.usergroup.UserGroup;
import com.backend.tcc.dto.user.UserResponseDTO;

public record GroupResponseDTO(
        String id,
        UserResponseDTO adm,
        String description,
        String image,
        String image_name,
        String name,
        UserGroup userGroups
        ) {
}
