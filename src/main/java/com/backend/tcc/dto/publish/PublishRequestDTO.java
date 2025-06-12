package com.backend.tcc.dto.publish;

import org.springframework.web.multipart.MultipartFile;


public record PublishRequestDTO(
        String id,
        MultipartFile image,
        String description,
        String author,
        String album) {
}
