package com.backend.tcc.dto.publish;

import java.util.List;

import com.backend.tcc.domain.image.Images;
import com.backend.tcc.dto.user.UserResponseDTO;

public record PublishResponseDTO(
        String id,
        List<Images> images,
        String description,
        UserResponseDTO author,
        String whenSent,
        int qtCommentary,
        String album) {
}
