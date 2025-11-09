package com.backend.tcc.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.constants.Constants;
import com.backend.tcc.domain.group.Group;
import com.backend.tcc.domain.group.album.Album;
import com.backend.tcc.domain.image.Images;
import com.backend.tcc.domain.user.User;
import com.backend.tcc.domain.usergroup.UserGroup;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.repositories.AlbumRepository;
import com.backend.tcc.repositories.GroupRepository;
import com.backend.tcc.repositories.ImageRepository;
import com.backend.tcc.repositories.UserGroupRepository;
import com.backend.tcc.repositories.UserRepository;
import com.backend.tcc.services.CloudinaryService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/userGroup")
@RequiredArgsConstructor
public class UserGroupController {
    private final UserGroupRepository repository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final AlbumRepository albumRepository;
    private final ImageRepository imageRepository;
    private final CloudinaryService cloudinaryService;

    @GetMapping
    public void resetNotifies(
        @RequestParam(required = true) String groupId,
        @RequestParam(required = true) String userId
    ) {
        UserGroup userGroup = repository.findByUserIdAndGroupId(userId, groupId);

        if(userGroup == null) {
            throw new PadraoException("Não foi possível encontrar nenhuma notificação para esse grupo");
        }

        userGroup.setHourLastPublish(null);
        userGroup.setTotalNotifies(0);
        repository.save(userGroup);
    }

    @GetMapping("/promoteToAdmin")
    public ResponseEntity<Void> promoteToAdmin(@RequestParam(required = true) String userId, @RequestParam(required = true) String groupId) {
        if(!userRepository.existsById(userId)) 
            throw new PadraoException("Id do usuário informado não existe");

        if(!groupRepository.existsById(groupId)) 
            throw new PadraoException("Id do grupo informado não existe");

        UserGroup userGroup = repository.findByUserIdAndGroupId(userId, groupId);

        if(userGroup != null) {
            userGroup.setAdm(true);
            repository.save(userGroup);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/removeFromAdminList")
    public ResponseEntity<Void> removeFromAdminList(@RequestParam(required = true) String userId, @RequestParam(required = true) String groupId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()) 
            throw new PadraoException("Id do usuário informado não existe");

        if(!groupRepository.existsById(groupId)) 
            throw new PadraoException("Id do grupo informado não existe");

        UserGroup userGroup = repository.findByUserIdAndGroupId(userId, groupId);

        if(userGroup == null) throw new PadraoException("Erro ao buscar UserGroup");
        
        if(userGroup.getGroup().getAdm().getId().equals(userId))
            throw new PadraoException("Não é possível remover " + user.get().getName() + " da lista de admins porque ele está como dono do grupo");

        try {
            userGroup.setAdm(false);
            repository.save(userGroup);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/removeFromGroup")
    public ResponseEntity<Void> removeFromGroup(@RequestParam(required = true) String userId, @RequestParam(required = true) String groupId) {
        if(!userRepository.existsById(userId)) 
            throw new PadraoException("Id do usuário informado não existe");

        if(!groupRepository.existsById(groupId)) 
            throw new PadraoException("Id do grupo informado não existe");

        UserGroup userGroup = repository.findByUserIdAndGroupId(userId, groupId);

        if(userGroup == null) throw new PadraoException("Erro ao buscar UserGroup");

        if(userGroup.getGroup().getAdm().getId().equals(userId)) 
            throw new PadraoException("Não é possível remover o dono do grupo");

        try {
            repository.delete(userGroup);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/promoteToGroupOwner")
    public ResponseEntity<Void> promoteToGroupOwner(@RequestParam(required = true) String userId, @RequestParam(required = true) String groupId) {
        if(!userRepository.existsById(userId)) 
            throw new PadraoException("Id do usuário informado não existe");

        if(!groupRepository.existsById(groupId)) 
            throw new PadraoException("Id do grupo informado não existe");

        UserGroup userGroup = repository.findByUserIdAndGroupId(userId, groupId);

        if(!userGroup.isAdm()) {
            userGroup.setAdm(true);
        }

        User user = userRepository.findById(userId).get();
        Group group = groupRepository.findById(groupId).get();
        group.setAdm(user);

        try {
            repository.save(userGroup);
            groupRepository.save(group);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/leaveGroup")
    @Transactional
    public ResponseEntity<Void> leaveGroup(@RequestParam(required = true) String userId, @RequestParam(required = true) String groupId) {
        if(!userRepository.existsById(userId)) 
            throw new PadraoException("Id do usuário informado não existe");

        if(!groupRepository.existsById(groupId)) 
            throw new PadraoException("Id do grupo informado não existe");
            
        UserGroup userGroup = repository.findByUserIdAndGroupId(userId, groupId);
        if(userGroup == null) throw new PadraoException("Erro ao buscar UserGroup");

        boolean groupHasMoreThanOnePerson = userGroup.getGroup().getUserGroups().size() > 1;

        if(userGroup.getGroup().getAdm().getId().equals(userId) && groupHasMoreThanOnePerson)
            throw new PadraoException("Promova um membro como dono do grupo antes de sair");

        try {
            repository.delete(userGroup);

            if(!groupHasMoreThanOnePerson) {
                List<Album> albums = albumRepository.findByGroupId(groupId);
                List<String> albumIds = albums.stream()
                               .map(Album::getId)
                               .collect(Collectors.toList());

                List<Images> images = imageRepository.findAllByPublish_AlbumIdIn(albumIds);

                Group group = groupRepository.findById(groupId).orElseThrow(() -> new PadraoException("Grupo não encontrado"));

                groupRepository.deleteById(groupId);

                cloudinaryService.deleteFileByUrl(group.getImage());

                for(Album album : albums) {
                    if(album.getImage().equals(Constants.ALBUM_NOIMAGE_URL)) continue;

                    try {
                        cloudinaryService.deleteFileByUrl(album.getImage());
                    } catch (Exception ex) {
                        System.err.println("Erro ao deletar imagem do álbum: " + ex.getMessage());
                    }
                }

                for(Images image : images) {
                    try {
                        cloudinaryService.deleteFileByUrl(image.getImage());
                    } catch (Exception ex) {
                        System.err.println("Erro ao deletar imagem: " + ex.getMessage());
                    }
                }
            }

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println(e);
            throw new PadraoException("Erro ao sair do grupo, tente novamente");
        }
    }

}
