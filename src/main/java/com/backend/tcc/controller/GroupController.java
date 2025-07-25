package com.backend.tcc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.dto.group.GroupRequestDTO;
import com.backend.tcc.dto.group.GroupResponseDTO;
import com.backend.tcc.dto.group.album.AlbumResponseDTO;
import com.backend.tcc.services.GroupService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService service;

    @GetMapping
    public ResponseEntity<List<GroupResponseDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
    
    @GetMapping("/userId/{id}")
    public ResponseEntity<List<GroupResponseDTO>> findAllByUserId(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findAllByUserId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping("/{id}/albums")
    public ResponseEntity<List<AlbumResponseDTO>> findAlbumsByGroupId(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findAlbumsByGroupId(id));
    }

    @PostMapping
    public ResponseEntity<GroupResponseDTO> save(@ModelAttribute GroupRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @PutMapping
    public ResponseEntity<GroupResponseDTO> update(@ModelAttribute GroupRequestDTO request) {
        return ResponseEntity.ok().body(service.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return ResponseEntity.ok().body(service.delete(id));
    }
}