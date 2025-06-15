package com.backend.tcc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.tcc.domain.publish.Publish;

public interface PublishRepository extends JpaRepository<Publish, String> {

    List<Publish> findByAlbumId(String id);
}
