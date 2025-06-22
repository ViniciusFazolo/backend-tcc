package com.backend.tcc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.backend.tcc.domain.group.Group;
import com.backend.tcc.dto.group.GroupRequestDTO;
import com.backend.tcc.dto.group.GroupResponseDTO;
import com.backend.tcc.utils.Utils;

@Mapper(componentModel = "spring", uses = {Utils.class, UserMapper.class})
public interface GroupMapper {

    @Mapping(target = "adm.id", source = "adm")
    @Mapping(source = "image", target = "image", qualifiedByName = "m2b")
    Group toEntity(GroupRequestDTO request);
    
    @Mapping(source = "image", target = "image", qualifiedByName = "b2b64")
    @Mapping(source = "userGroups", target = "userGroups", qualifiedByName = "firstElement")
    GroupResponseDTO toDto(Group obj);
}
