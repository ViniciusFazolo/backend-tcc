package com.backend.tcc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.backend.tcc.domain.group.album.Album;
import com.backend.tcc.domain.publish.Publish;
import com.backend.tcc.domain.user.User;
import com.backend.tcc.dto.publish.PublishRequestDTO;
import com.backend.tcc.dto.publish.PublishResponseDTO;
import com.backend.tcc.utils.Utils;

@Mapper(componentModel = "spring", uses = Utils.class)
public interface PublishMapper {

    @Mapping(source = "image", target = "image", qualifiedByName = "m2b")
    @Mapping(source = "author", target = "author")
    @Mapping(source = "album", target = "album")
    @Mapping(target = "whenSent", expression = "java(java.time.LocalDateTime.now())")
    Publish toEntity(PublishRequestDTO request);

    @Mapping(source = "image", target = "image", qualifiedByName = "b2b64")
    @Mapping(source = "author.id", target = "author")
    @Mapping(source = "album.id", target = "album")
    PublishResponseDTO toDto(Publish obj);

    default User map(String userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }

    default Album mapAlbum(String albumId) {
        if (albumId == null) return null;
        Album album = new Album();
        album.setId(albumId);
        return album;
    }
}
