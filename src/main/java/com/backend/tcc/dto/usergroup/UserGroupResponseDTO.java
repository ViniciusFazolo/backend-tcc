package com.backend.tcc.dto.usergroup;

import java.time.LocalTime;

import com.backend.tcc.dto.user.UserResponseDTO;

public record UserGroupResponseDTO(
    String id,
    UserResponseDTO user,
    boolean adm,
    Integer totalNotifies,
    LocalTime hourLastPublish
) {
    
}
