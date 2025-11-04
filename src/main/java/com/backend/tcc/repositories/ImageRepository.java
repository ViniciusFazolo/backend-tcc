package com.backend.tcc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.tcc.domain.image.Images;

public interface ImageRepository extends JpaRepository<Images, String>{
    List<Images> findAllByPublish_AlbumIdIn(List<String> ids);
}
