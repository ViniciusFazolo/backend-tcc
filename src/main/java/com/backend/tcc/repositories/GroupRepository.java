package com.backend.tcc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.tcc.domain.group.Group;


@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

}
