package com.backend.tcc.services;

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
}
