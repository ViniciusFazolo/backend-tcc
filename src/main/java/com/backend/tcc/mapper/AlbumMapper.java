package com.backend.tcc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.backend.tcc.domain.group.Group;
import com.backend.tcc.domain.group.album.Album;
import com.backend.tcc.dto.group.album.AlbumRequestDTO;
import com.backend.tcc.dto.group.album.AlbumResponseDTO;
import com.backend.tcc.utils.Utils;

@Mapper(componentModel = "spring", uses = Utils.class)
public interface AlbumMapper {

    @Mapping(source = "image", target = "image", qualifiedByName = "m2b")
    @Mapping(source = "group", target = "group", qualifiedByName = "mapGroup")
    Album toEntity(AlbumRequestDTO request);

    @Mapping(source = "image", target = "image", qualifiedByName = "b2b64")
    @Mapping(target = "group", source = "group.id")
    AlbumResponseDTO toDto(Album obj);
    
    @Named("mapGroup")
    default Group mapGroup(String groupId) {
        if (groupId == null || groupId.trim().isEmpty()) {
            return null;
        }
        Group group = new Group();
        group.setId(groupId);
        return group;
    }
}
