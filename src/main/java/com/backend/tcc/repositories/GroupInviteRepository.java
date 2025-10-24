package com.backend.tcc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.tcc.domain.groupinvite.GroupInvite;
import com.backend.tcc.domain.user.User;

public interface GroupInviteRepository extends JpaRepository<GroupInvite, String> {
    List<GroupInvite> findByInvitedUserAndStatus(User invitedUser, GroupInvite.Status status);
    List<GroupInvite> findByGroupIdAndStatus(String id, GroupInvite.Status status);
}
