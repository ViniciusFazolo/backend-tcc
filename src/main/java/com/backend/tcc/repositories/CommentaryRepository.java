package com.backend.tcc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.tcc.domain.commentary.Commentary;

public interface CommentaryRepository extends JpaRepository<Commentary, String> {

}
