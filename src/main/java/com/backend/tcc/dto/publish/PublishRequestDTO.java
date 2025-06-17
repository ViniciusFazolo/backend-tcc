package com.backend.tcc.dto.publish;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public record PublishRequestDTO(
        String id,
        List<MultipartFile> images,
        String description,
        String author,
        String album) {
}
