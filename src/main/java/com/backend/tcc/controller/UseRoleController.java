package com.backend.tcc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.domain.user.UserRole;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.repositories.UserRoleRepository;

@RestController
@RequestMapping("/role")
public class UseRoleController {
    @Autowired
    private UserRoleRepository roleRepository;

    @GetMapping
    public List<UserRole> getRoles(){
        return roleRepository.findAll();
    }

    @PostMapping
    public UserRole criar(@RequestBody UserRole obj){
        Optional<UserRole> role = roleRepository.findByRole(obj.getRole());

        if(role.isPresent()){
            throw new PadraoException("Esse cargo já existe");
        }

        return roleRepository.save(obj);
    }
}
