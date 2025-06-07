package com.backend.tcc.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.dto.group.album.AlbumRequestDTO;
import com.backend.tcc.dto.group.album.AlbumResponseDTO;
import com.backend.tcc.services.AlbumService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/album")
@RequiredArgsConstructor
public class AlbumController {
    private final AlbumService service;

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> save(@ModelAttribute AlbumRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }
}
