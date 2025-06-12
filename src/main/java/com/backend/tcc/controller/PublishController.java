package com.backend.tcc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.dto.publish.PublishRequestDTO;
import com.backend.tcc.dto.publish.PublishResponseDTO;
import com.backend.tcc.services.PublishService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/publish")
@RequiredArgsConstructor
public class PublishController {
    private final PublishService service;

    @GetMapping
    public ResponseEntity<List<PublishResponseDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }


    @PostMapping
    public ResponseEntity<PublishResponseDTO> save(@ModelAttribute PublishRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(request));
    }

}
