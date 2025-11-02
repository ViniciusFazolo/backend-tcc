package com.backend.tcc.mapper;

import org.mapstruct.Mapper;

import com.backend.tcc.domain.usergroup.UserGroup;
import com.backend.tcc.dto.usergroup.UserGroupResponseDTO;

@Mapper(componentModel = "spring")
public interface UserGroupMapper {

    UserGroupResponseDTO toDto(UserGroup entity);
}
