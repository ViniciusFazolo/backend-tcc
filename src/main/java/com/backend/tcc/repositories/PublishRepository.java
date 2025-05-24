package com.backend.tcc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.tcc.domain.publish.Publish;

public interface PublishRepository extends JpaRepository<Publish, String> {

}
