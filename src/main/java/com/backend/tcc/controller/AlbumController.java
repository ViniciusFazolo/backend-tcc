package com.backend.tcc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping
    public ResponseEntity<List<AlbumResponseDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<AlbumResponseDTO>> findByGroupId(@PathVariable String groupId) {
        return ResponseEntity.ok().body(service.findByGroupId(groupId));
    }
    
    @DeleteMapping
    public void delete(@RequestBody List<String> ids) {
        service.delete(ids);
    }
}
