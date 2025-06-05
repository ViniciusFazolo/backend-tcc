package com.backend.tcc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.backend.tcc.domain.user.User;
import com.backend.tcc.dto.user.UserRequestDTO;
import com.backend.tcc.dto.user.UserResponseDTO;
import com.backend.tcc.dto.user.auth.ResponseUserDTO;
import com.backend.tcc.utils.Utils;

@Mapper(componentModel = "spring", uses = Utils.class)
public interface UserMapper {
    
    @Mapping(source="image", target = "image", qualifiedByName = "m2b")
    User toEntity(UserRequestDTO request);
    
    @Mapping(source="image", target = "image", qualifiedByName = "b2b64")
    UserResponseDTO toDto(User obj);

    ResponseUserDTO toDTO(User obj);

}
