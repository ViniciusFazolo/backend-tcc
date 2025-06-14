package com.backend.tcc.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.tcc.domain.group.album.Album;
import com.backend.tcc.dto.group.album.AlbumRequestDTO;
import com.backend.tcc.dto.group.album.AlbumResponseDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.mapper.AlbumMapper;
import com.backend.tcc.repositories.AlbumRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumMapper mapper;
    private final AlbumRepository repository;

    public AlbumResponseDTO save(AlbumRequestDTO request) {
        try {
            Album entity = mapper.toEntity(request);
            return mapper.toDto(repository.save(entity));
        } catch (Exception e) {
            throw new PadraoException("Erro ao criar álbum");
        }
    }

    public List<AlbumResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<AlbumResponseDTO> findByGroupId(String groupId) {
        return repository.findByGroupId(groupId).stream()
                .map(mapper::toDto)
                .toList();
    }
}
