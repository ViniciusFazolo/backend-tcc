package com.backend.tcc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.backend.tcc.domain.publish.Publish;
import com.backend.tcc.dto.publish.PublishRequestDTO;
import com.backend.tcc.dto.publish.PublishResponseDTO;
import com.backend.tcc.utils.Utils;

@Mapper(componentModel = "spring", uses = {Utils.class, UserMapper.class})
public interface PublishMapper {

    @Mapping(source = "author", target = "author.id")
    @Mapping(source = "album", target = "album.id")
    @Mapping(target = "whenSent", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(source = "images", target = "images", qualifiedByName = "mList2bList")
    Publish toEntity(PublishRequestDTO request);
    
    @Mapping(source = "images", target = "images", qualifiedByName = "bList2b64List")
    @Mapping(source = "album.id", target = "album")
    PublishResponseDTO toDto(Publish obj);

}
