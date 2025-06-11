package com.backend.tcc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.tcc.domain.group.album.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, String> {
    List<Album> findByGroupId(String groupId);
}
