package com.backend.tcc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.tcc.domain.usergroup.UserGroup;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, String> {
    
    UserGroup findByUserIdAndGroupId(String userId, String groupId);
    List<UserGroup> findByGroupId(String groupId);
}
