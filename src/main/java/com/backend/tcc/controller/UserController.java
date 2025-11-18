package com.backend.tcc.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.dto.user.UserRequestDTO;
import com.backend.tcc.dto.user.UserResponseDTO;
import com.backend.tcc.dto.user.auth.RegisterUserDTO;
import com.backend.tcc.dto.user.auth.ResponseUserDTO;
import com.backend.tcc.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @GetMapping("/existsByLogin/{login}")
    public ResponseEntity<Boolean> existsByLogin(@PathVariable String login) {
        return ResponseEntity.ok().body(service.existsByLogin(login));
    }
    
    @GetMapping("/byGroupId/{id}")
    public ResponseEntity<List<UserResponseDTO>> findByGroupId(@PathVariable String id) {
        return service.findByGroupId(id);
    }

    @PostMapping
    public ResponseEntity<ResponseUserDTO> save(@RequestBody RegisterUserDTO request) {
        return service.save(request);
    }

    @PutMapping
    public ResponseEntity<ResponseUserDTO> update(@ModelAttribute UserRequestDTO request) {
        return service.update(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return service.delete(id);
    }
}