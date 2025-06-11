package com.backend.tcc.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.tcc.domain.group.Group;
import com.backend.tcc.dto.group.GroupRequestDTO;
import com.backend.tcc.dto.group.GroupResponseDTO;
import com.backend.tcc.dto.group.album.AlbumResponseDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.mapper.AlbumMapper;
import com.backend.tcc.mapper.GroupMapper;
import com.backend.tcc.repositories.AlbumRepository;
import com.backend.tcc.repositories.GroupRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository repository;
    private final GroupMapper mapper;
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    public List<GroupResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public GroupResponseDTO findById(String id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new PadraoException("Grupo não encontrado"));
    }

    public List<AlbumResponseDTO> findAlbumsByGroupId(String groupId) {
        repository.findById(groupId)
                .orElseThrow(() -> new PadraoException("Grupo não encontrado"));
        return albumRepository.findByGroupId(groupId).stream()
                .map(albumMapper::toDto)
                .toList();
    }

    public GroupResponseDTO save(GroupRequestDTO request) {
        try {
            Group entity = mapper.toEntity(request);
            return mapper.toDto(repository.save(entity));
        } catch (Exception e) {
            throw new PadraoException("Erro ao criar grupo");
        }
    }

    public GroupResponseDTO update(GroupRequestDTO request) {
        try {
            Group entity = repository.findById(request.id())
                    .orElseThrow(() -> new PadraoException("Grupo não encontrado"));
            entity = mapper.toEntity(request);
            return mapper.toDto(repository.save(entity));
        } catch (Exception e) {
            throw new PadraoException("Erro ao atualizar grupo");
        }
    }

    public String delete(String id) {
        try {
            repository.deleteById(id);

            return "Deletado com sucesso";
        } catch (Exception e) {
            throw new PadraoException("Erro ao deletar grupo");
        }
    }
}
