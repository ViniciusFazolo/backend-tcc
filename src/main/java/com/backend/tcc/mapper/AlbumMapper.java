package com.backend.tcc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.backend.tcc.domain.group.album.Album;
import com.backend.tcc.dto.group.album.AlbumRequestDTO;
import com.backend.tcc.dto.group.album.AlbumResponseDTO;
import com.backend.tcc.utils.Utils;

@Mapper(componentModel = "spring", uses = Utils.class)
public interface AlbumMapper {

    @Mapping(source = "image", target = "image", qualifiedByName = "m2b")
    @Mapping(source = "group", target = "group.id")
    Album toEntity(AlbumRequestDTO request);

    @Mapping(source = "image", target = "image", qualifiedByName = "b2b64")
    @Mapping(target = "group", source = "group.id")
    AlbumResponseDTO toDto(Album obj);
}
