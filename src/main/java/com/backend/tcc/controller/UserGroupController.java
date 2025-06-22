package com.backend.tcc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.domain.usergroup.UserGroup;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.repositories.UserGroupRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/userGroup")
@RequiredArgsConstructor
public class UserGroupController {
    private final UserGroupRepository repository;

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
}
