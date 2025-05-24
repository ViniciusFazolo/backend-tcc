package com.backend.tcc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.backend.tcc.domain.commentary.Commentary;
import com.backend.tcc.dto.commentary.CommentaryRequestDTO;
import com.backend.tcc.dto.commentary.CommentaryResponseDTO;

@Mapper(componentModel = "spring")
public interface CommentaryMapper {

    @Mapping(source = "author", target = "author.id")
    @Mapping(source = "publish", target = "publish.id")
    Commentary toEntity(CommentaryRequestDTO request);

    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "publish.id", target = "publish")
    CommentaryResponseDTO toDto(Commentary request);
}
