package com.backend.tcc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.backend.tcc.domain.publish.Publish;
import com.backend.tcc.domain.userpublish.UserPublish;
import com.backend.tcc.dto.publish.PublishRequestDTO;
import com.backend.tcc.dto.publish.PublishResponseDTO;
import com.backend.tcc.utils.Utils;

@Mapper(componentModel = "spring", uses = {Utils.class, UserMapper.class})
public interface PublishMapper {

    @Mapping(source = "album", target = "album.id")
    @Mapping(target = "whenSent", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "images", ignore = true)
    Publish toEntity(PublishRequestDTO request);
    
    @Mapping(source = "album.id", target = "album")
    PublishResponseDTO toDto(Publish obj);
    
    @Mapping(source = "publish.id", target = "id")
    @Mapping(source = "publish.description", target = "description")
    @Mapping(source = "user", target = "author")
    @Mapping(source = "publish.whenSent", target = "whenSent")
    @Mapping(source = "publish.album.id", target = "album")
    PublishResponseDTO toDto(UserPublish obj);
}
