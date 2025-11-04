package com.backend.tcc.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.domain.group.Group;
import com.backend.tcc.domain.user.User;
import com.backend.tcc.domain.usergroup.UserGroup;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.repositories.GroupRepository;
import com.backend.tcc.repositories.UserGroupRepository;
import com.backend.tcc.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/userGroup")
@RequiredArgsConstructor
public class UserGroupController {
    private final UserGroupRepository repository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

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

}
