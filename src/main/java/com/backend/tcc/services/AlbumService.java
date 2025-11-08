package com.backend.tcc.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.tcc.constants.Constants;
import com.backend.tcc.domain.group.album.Album;
import com.backend.tcc.domain.image.Images;
import com.backend.tcc.dto.group.album.AlbumRequestDTO;
import com.backend.tcc.dto.group.album.AlbumResponseDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.mapper.AlbumMapper;
import com.backend.tcc.repositories.AlbumRepository;
import com.backend.tcc.repositories.ImageRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumMapper mapper;
    private final AlbumRepository repository;
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;

    public AlbumResponseDTO save(AlbumRequestDTO request) {
        try {
            Album entity = mapper.toEntity(request);

            if (request.image() != null && !request.image().isEmpty()) {
                String imageUrl = cloudinaryService.uploadFile(request.image());
                entity.setImage(imageUrl);
            } else {
                entity.setImage(Constants.ALBUM_NOIMAGE_URL);
            }

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

    @Transactional
    public void delete(List<String> ids) {
        List<Album> albums = repository.findAllById(ids);
        List<Images> images = imageRepository.findAllByPublish_AlbumIdIn(ids);

        try {
            for (Album album : albums) {
                if (album.getImage() != null && !album.getImage().equals(Constants.ALBUM_NOIMAGE_URL)) {
                    try {
                        cloudinaryService.deleteFileByUrl(album.getImage());
                    } catch (Exception ex) {
                        System.err.println("Erro ao deletar imagem do álbum: " + ex.getMessage());
                    }
                }
            }

            for (Images image : images) {
                if (image.getImage() != null) {
                    try {
                        cloudinaryService.deleteFileByUrl(image.getImage());
                    } catch (Exception ex) {
                        System.err.println("Erro ao deletar imagem: " + ex.getMessage());
                    }
                }
            }

            repository.deleteAll(albums);

        } catch (Exception e) {
            throw new PadraoException("Erro ao excluir álbum, tente novamente");
        }
    }
}
