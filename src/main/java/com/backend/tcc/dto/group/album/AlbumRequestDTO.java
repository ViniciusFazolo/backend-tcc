package com.backend.tcc.dto.group.album;

import org.springframework.web.multipart.MultipartFile;

public record AlbumRequestDTO(
    MultipartFile image,
    String name,
    String group
) {
    
}
