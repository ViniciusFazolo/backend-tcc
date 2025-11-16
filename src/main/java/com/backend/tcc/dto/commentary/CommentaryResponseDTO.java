package com.backend.tcc.dto.commentary;

import java.time.LocalDateTime;

import com.backend.tcc.dto.user.UserResponseDTO;

public record CommentaryResponseDTO(
    String id,
    String publish,
    UserResponseDTO author,
    String content,
    LocalDateTime whenSent
) {

}
