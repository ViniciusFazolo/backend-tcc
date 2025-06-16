package com.backend.tcc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.backend.tcc.domain.group.Group;


@Repository
public interface GroupRepository extends JpaRepository<Group, String> {

    @Query("SELECT g FROM groups g JOIN g.users u WHERE u.id = :userId")
    List<Group> findByUserId(@Param("userId") String userId);

}
