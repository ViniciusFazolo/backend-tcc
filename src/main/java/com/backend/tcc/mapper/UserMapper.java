package com.backend.tcc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.backend.tcc.domain.user.User;
import com.backend.tcc.dto.user.UserRequestDTO;
import com.backend.tcc.dto.user.UserResponseDTO;
import com.backend.tcc.dto.user.auth.ResponseUserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "image", ignore = true)
    User toEntity(UserRequestDTO request);
    UserResponseDTO toDto(User obj);

    ResponseUserDTO toDTO(User obj);

}
