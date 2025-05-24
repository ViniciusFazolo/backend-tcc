package com.backend.tcc.dto.commentary;

public record CommentaryRequestDTO(
    String id,
    String publish,
    String author,
    String content
) {

}
