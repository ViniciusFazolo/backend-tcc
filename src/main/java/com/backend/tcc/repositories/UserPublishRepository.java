package com.backend.tcc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.tcc.domain.userpublish.UserPublish;

public interface UserPublishRepository extends JpaRepository<UserPublish, String> {
    @Query("""
        SELECT up FROM UserPublish up
        JOIN up.user u
        JOIN up.publish p
        WHERE p.album.id = :albumId
        ORDER BY p.whenSent DESC
    """)
    List<UserPublish> findByAlbumId(@Param("albumId") String albumId);
}
