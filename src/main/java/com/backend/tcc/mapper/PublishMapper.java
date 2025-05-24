package com.backend.tcc.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.backend.tcc.domain.publish.Publish;
import com.backend.tcc.dto.publish.PublishRequestDTO;
import com.backend.tcc.dto.publish.PublishResponseDTO;
import com.backend.tcc.utils.Utils;

@Mapper(componentModel = "spring", uses = Utils.class)
public interface PublishMapper {

    @Mapping(source = "image", target = "image", qualifiedByName = "m2b")
    Publish toEntity(PublishRequestDTO request);

    @Mapping(source = "image", target = "image", qualifiedByName = "b2b64")
    PublishResponseDTO toDto(Publish obj);
}
