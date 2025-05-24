package com.backend.tcc.dto.commentary;

import java.time.LocalDateTime;

public record CommentaryResponseDTO(
    String id,
    String publish,
    String author,
    String content,
    LocalDateTime whenSent
) {

}
