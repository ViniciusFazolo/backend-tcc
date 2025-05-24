package com.backend.tcc.dto.publish;

import java.util.List;

import com.backend.tcc.domain.commentary.Commentary;
import com.backend.tcc.domain.user.User;

public record PublishResponseDTO(
        String id,
        String image,
        String description,
        User author,
        List<Commentary> commentaries) {
}
