package com.backend.tcc.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.tcc.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByLogin(String login);

    @Query("SELECT u FROM users u JOIN u.userGroups ug WHERE ug.group.id = :groupId")
    List<User> findByGroupId(@Param("groupId") String id);
}
