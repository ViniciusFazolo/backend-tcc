package com.backend.tcc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.tcc.domain.image.Images;

public interface ImageRepository extends JpaRepository<Images, String>{
    
}
