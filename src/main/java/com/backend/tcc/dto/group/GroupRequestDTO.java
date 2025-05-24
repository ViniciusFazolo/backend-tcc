package com.backend.tcc.dto.group;

import org.springframework.web.multipart.MultipartFile;

public record GroupRequestDTO(
        String id,
        String name,
        String description,
        String image_name,
        MultipartFile image,
        String adm) {

}
