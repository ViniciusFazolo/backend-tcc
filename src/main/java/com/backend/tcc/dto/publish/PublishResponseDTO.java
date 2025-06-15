package com.backend.tcc.dto.publish;

import com.backend.tcc.dto.user.UserResponseDTO;

public record PublishResponseDTO(
        String id,
        String image,
        String description,
        UserResponseDTO author,
        String whenSent,
        String album) {
}
