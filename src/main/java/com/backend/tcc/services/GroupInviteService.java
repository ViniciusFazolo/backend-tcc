package com.backend.tcc.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.backend.tcc.domain.group.Group;
import com.backend.tcc.domain.groupinvite.GroupInvite;
import com.backend.tcc.domain.user.User;
import com.backend.tcc.domain.usergroup.UserGroup;
import com.backend.tcc.dto.groupinvite.GroupInviteRequestDTO;
import com.backend.tcc.dto.groupinvite.GroupInviteResponseDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.mapper.UserMapper;
import com.backend.tcc.repositories.GroupInviteRepository;
import com.backend.tcc.repositories.GroupRepository;
import com.backend.tcc.repositories.UserGroupRepository;
import com.backend.tcc.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GroupInviteService {
    private final GroupInviteRepository inviteRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserMapper userMapper;

    public void sendInvite(GroupInviteRequestDTO request) {
        Group group = groupRepository.findById(request.groupId())
                .orElseThrow(() -> new PadraoException("Grupo não encontrado"));

        User invitedUser = userRepository.findByLogin(request.receiverLogin())
                .orElseThrow(() -> new PadraoException("Usuário a ser convidado não encontrado"));

        User invitedBy = userRepository.findById(request.senderId())
                .orElseThrow(() -> new PadraoException("Usuário que convida não encontrado"));

        // Verifica se já existe convite pendente
        boolean exists = inviteRepository.findByInvitedUserAndStatus(invitedUser, GroupInvite.Status.PENDING)
                .stream()
                .anyMatch(invite -> invite.getGroup().getId().equals(group.getId()));

        if (exists) {
            throw new PadraoException("Convite já enviado para este usuário");
        }

        GroupInvite invite = GroupInvite.builder()
                .group(group)
                .invitedUser(invitedUser)
                .invitedBy(invitedBy)
                .status(GroupInvite.Status.PENDING)
                .build();

        inviteRepository.save(invite);
    }

    public List<GroupInviteResponseDTO> getPendingInvites(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new PadraoException("Usuário não encontrado"));

        return inviteRepository.findByInvitedUserAndStatus(user, GroupInvite.Status.PENDING)
                .stream()
                .map(invite -> new GroupInviteResponseDTO(
                        invite.getId(),
                        invite.getGroup().getId(),
                        invite.getGroup().getName(),
                        userMapper.toDto(invite.getInvitedBy()),
                        invite.getStatus().name()
                ))
                .collect(Collectors.toList());
    }

    public void respondInvite(String inviteId, boolean accept) {
        GroupInvite invite = inviteRepository.findById(inviteId)
                .orElseThrow(() -> new PadraoException("Convite não encontrado"));

        if (invite.getStatus() != GroupInvite.Status.PENDING) {
            throw new PadraoException("Convite já respondido");
        }

        if (accept) {
            invite.setStatus(GroupInvite.Status.ACCEPTED);
            // Adiciona o usuário ao grupo
            UserGroup userGroup = new UserGroup();
            userGroup.setGroup(invite.getGroup());
            userGroup.setUser(invite.getInvitedUser());
            userGroupRepository.save(userGroup);
        } else {
            invite.setStatus(GroupInvite.Status.DECLINED);
        }

        inviteRepository.save(invite);
    }
}
