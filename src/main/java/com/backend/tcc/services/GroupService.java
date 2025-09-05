package com.backend.tcc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.tcc.constants.Contants;
import com.backend.tcc.domain.group.Group;
import com.backend.tcc.domain.user.User;
import com.backend.tcc.domain.usergroup.UserGroup;
import com.backend.tcc.dto.group.GroupRequestDTO;
import com.backend.tcc.dto.group.GroupResponseDTO;
import com.backend.tcc.dto.group.album.AlbumResponseDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.mapper.AlbumMapper;
import com.backend.tcc.mapper.GroupMapper;
import com.backend.tcc.repositories.AlbumRepository;
import com.backend.tcc.repositories.GroupRepository;
import com.backend.tcc.repositories.UserGroupRepository;
import com.backend.tcc.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository repository;
    private final GroupMapper mapper;
    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final CloudinaryService cloudinaryService;

    public List<GroupResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }
  
    public List<GroupResponseDTO> findAllByUserId(String id) {
        return repository.findByUserId(id)
                .stream()
                .map(group -> {
                    
                    Optional<UserGroup> ugObj = group.getUserGroups()
                    .stream()
                            .filter(ug -> ug.getGroup().getId().equals(group.getId()) && ug.getUser().getId().equals(id))
                            .findFirst();
                            
                    group.getUserGroups().clear();
                    if(ugObj.isPresent())  group.getUserGroups().add(ugObj.get());
                    return mapper.toDto(group);
                })
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
            User user = userRepository.findById(request.adm()).orElseThrow(() -> new PadraoException("Usuário não encontrado"));
            Group entity = mapper.toEntity(request);

            if (request.image() != null && !request.image().isEmpty()) {
                String imageUrl = cloudinaryService.uploadFile(request.image());
                entity.setImage(imageUrl);
            }else{
                entity.setImage(Contants.GROUP_NOIMAGE_URL);
            }

            entity = repository.save(entity);

            UserGroup userGroup = new UserGroup();
            userGroup.setGroup(entity);
            userGroup.setUser(user);
            userGroupRepository.save(userGroup);
            
            return mapper.toDto(entity);
        } catch (Exception e) {
            System.out.println(e);
            throw new PadraoException("Erro ao criar grupo");
        }
    }

    public GroupResponseDTO update(GroupRequestDTO request) {
        try {
            Group entity = repository.findById(request.id())
                    .orElseThrow(() -> new PadraoException("Grupo não encontrado"));
            entity = mapper.toEntity(request);
            String oldImage = entity.getImage();

            if (request.image() != null && !request.image().isEmpty()) {
                cloudinaryService.deleteFileByUrl(oldImage);
                
                String newImageUrl = cloudinaryService.uploadFile(request.image());
                entity.setImage(newImageUrl);
            }else{
                entity.setImage(oldImage);
            }

            return mapper.toDto(repository.save(entity));
        } catch (Exception e) {
            throw new PadraoException("Erro ao atualizar grupo");
        }
    }

    public String delete(String id) {
        try {
            Group obj = repository.findById(id).orElseThrow(() -> new PadraoException("Grupo não encontrado"));

            repository.deleteById(id);
            cloudinaryService.deleteFileByUrl(obj.getImage());

            return "Deletado com sucesso";
        } catch (Exception e) {
            throw new PadraoException("Erro ao deletar grupo");
        }
    }
}
