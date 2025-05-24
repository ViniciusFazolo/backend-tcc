package com.backend.tcc.dto.publish;

import org.springframework.web.multipart.MultipartFile;

import com.backend.tcc.domain.group.Group;
import com.backend.tcc.domain.user.User;

public record PublishRequestDTO(
        String id,
        MultipartFile image,
        String description,
        User author,
        Group group) {
}
