package com.backend.tcc.dto.publish;

import java.util.List;

import com.backend.tcc.dto.user.UserResponseDTO;

public record PublishResponseDTO(
        String id,
        List<String> images,
        String description,
        UserResponseDTO author,
        String whenSent,
        String album) {
}
