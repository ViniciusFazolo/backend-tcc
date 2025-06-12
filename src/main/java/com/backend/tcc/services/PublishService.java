package com.backend.tcc.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.tcc.domain.publish.Publish;
import com.backend.tcc.dto.publish.PublishRequestDTO;
import com.backend.tcc.dto.publish.PublishResponseDTO;
import com.backend.tcc.mapper.PublishMapper;
import com.backend.tcc.repositories.PublishRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublishService {
    private final PublishRepository repository;
    private final PublishMapper mapper;

    public List<PublishResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public PublishResponseDTO save(PublishRequestDTO request) {
        try {
            Publish entity = mapper.toEntity(request);
            return mapper.toDto(repository.save(entity));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar publicação", e);
        }
    }

}
