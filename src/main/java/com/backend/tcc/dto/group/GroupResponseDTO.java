package com.backend.tcc.dto.group;

import java.util.List;

import com.backend.tcc.domain.usergroup.UserGroup;
import com.backend.tcc.dto.user.UserResponseDTO;

public record GroupResponseDTO(
        String id,
        UserResponseDTO adm,
        String description,
        String image,
        String image_name,
        String name,
        List<UserGroup> userGroups
        ) {
}
