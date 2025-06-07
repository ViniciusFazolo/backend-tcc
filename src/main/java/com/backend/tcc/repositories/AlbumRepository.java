package com.backend.tcc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.tcc.domain.group.album.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {
    
}
