package com.backend.tcc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.dto.commentary.CommentaryRequestDTO;
import com.backend.tcc.dto.commentary.CommentaryResponseDTO;
import com.backend.tcc.services.CommentaryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/commentary")
@RequiredArgsConstructor
public class CommentaryController {
    private final CommentaryService service;

    @GetMapping
    public ResponseEntity<List<CommentaryResponseDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentaryResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping("/byPublishId/{id}")
    public ResponseEntity<List<CommentaryResponseDTO>> findByPublishId(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findByPublishId(id));
    }

    @PostMapping
    public ResponseEntity<CommentaryResponseDTO> save(@RequestBody CommentaryRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

    @PutMapping
    public ResponseEntity<CommentaryResponseDTO> update(@RequestBody CommentaryRequestDTO request) {
        return ResponseEntity.ok().body(service.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return ResponseEntity.ok().body(service.delete(id));
    }
}