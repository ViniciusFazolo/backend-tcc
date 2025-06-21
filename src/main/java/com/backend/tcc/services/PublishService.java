package com.backend.tcc.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.tcc.domain.publish.Publish;
import com.backend.tcc.domain.user.User;
import com.backend.tcc.domain.userpublish.UserPublish;
import com.backend.tcc.dto.publish.PublishRequestDTO;
import com.backend.tcc.dto.publish.PublishResponseDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.mapper.PublishMapper;
import com.backend.tcc.repositories.PublishRepository;
import com.backend.tcc.repositories.UserPublishRepository;
import com.backend.tcc.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublishService {
    private final PublishRepository repository;
    private final PublishMapper mapper;
    private final UserRepository userRepository;
    private final UserPublishRepository userPublishRepository;

    public List<PublishResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<PublishResponseDTO> findByAlbumId(String id) {
        List<UserPublish> userPublishs = userPublishRepository.findByAlbumId(id);

        List<PublishResponseDTO> objs = userPublishs
            .stream()
            .map(mapper::toDto).toList();

        return objs;
    }

    public PublishResponseDTO save(PublishRequestDTO request) {
        try {
            User user = userRepository.findById(request.author())
                    .orElseThrow(() -> new PadraoException("Usuário não encontrado"));

            Publish entity = mapper.toEntity(request);
            entity = repository.save(entity);
            
            UserPublish userPublish = new UserPublish();
            userPublish.setUser(user);
            userPublish.setPublish(entity);

            userPublishRepository.save(userPublish);

            return mapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar publicação", e);
        }
    }

}
