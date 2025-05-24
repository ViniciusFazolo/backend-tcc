package com.backend.tcc.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.tcc.domain.user.User;
import com.backend.tcc.dto.user.UserRequestDTO;
import com.backend.tcc.dto.user.UserResponseDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.mapper.UserMapper;
import com.backend.tcc.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public UserResponseDTO findById(String id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new PadraoException("Usuário não encontrado"));
    }

    public UserResponseDTO save(UserRequestDTO request) {
        try {
            User entity = mapper.toEntity(request);
            return mapper.toDto(repository.save(entity));
        } catch (Exception e) {
            throw new PadraoException("Erro ao criar usuário");
        }
    }

    public UserResponseDTO update(UserRequestDTO request) {
        try {
            User entity = repository.findById(request.id())
                    .orElseThrow(() -> new PadraoException("Usuário não encontrado"));
            entity = mapper.toEntity(request);
            return mapper.toDto(repository.save(entity));
        } catch (Exception e) {
            throw new PadraoException("Erro ao atualizar usuário");
        }
    }

    public String delete(String id) {
        try {
            repository.deleteById(id);

            return "Deletado com sucesso";
        } catch (Exception e) {
            throw new PadraoException("Erro ao deletar usuário");
        }
    }
}
