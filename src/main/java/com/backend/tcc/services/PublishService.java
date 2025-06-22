package com.backend.tcc.services;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.tcc.domain.group.album.Album;
import com.backend.tcc.domain.publish.Publish;
import com.backend.tcc.domain.user.User;
import com.backend.tcc.domain.usergroup.UserGroup;
import com.backend.tcc.domain.userpublish.UserPublish;
import com.backend.tcc.dto.publish.PublishRequestDTO;
import com.backend.tcc.dto.publish.PublishResponseDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.mapper.PublishMapper;
import com.backend.tcc.repositories.AlbumRepository;
import com.backend.tcc.repositories.PublishRepository;
import com.backend.tcc.repositories.UserGroupRepository;
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
    private final UserGroupRepository userGroupRepository;
    private final AlbumRepository albumRepository;

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


            Album album = albumRepository.findById(request.album()).orElseThrow(() -> new PadraoException("Album não encontrado"));
            List<UserGroup> userGroup = userGroupRepository.findByGroupId(album.getGroup().getId());
            userGroup.stream().forEach(ug -> {
                ug.setTotalNotifies(ug.getTotalNotifies() + 1);
                ug.setHourLastPublish(LocalTime.now());
            });
            userGroupRepository.saveAll(userGroup);

            return mapper.toDto(entity);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar publicação", e);
        }
    }

}
