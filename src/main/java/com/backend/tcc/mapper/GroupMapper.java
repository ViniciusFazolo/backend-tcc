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
    @Mapping(target = "image", ignore = true)
    Group toEntity(GroupRequestDTO request);
    
    GroupResponseDTO toDto(Group obj);
}
