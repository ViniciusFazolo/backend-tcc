package com.backend.tcc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.tcc.domain.user.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    UserRole findByRole(String role);
}
