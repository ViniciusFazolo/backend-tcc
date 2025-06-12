package com.backend.tcc.dto.publish;


public record PublishResponseDTO(
        String id,
        String image,
        String description,
        String author,
        String album) {
}
