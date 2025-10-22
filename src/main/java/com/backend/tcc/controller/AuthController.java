package com.backend.tcc.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.tcc.constants.Contants;
import com.backend.tcc.domain.user.User;
import com.backend.tcc.domain.user.UserRole;
import com.backend.tcc.dto.user.auth.LoginRequestDTO;
import com.backend.tcc.dto.user.auth.LoginResponseDTO;
import com.backend.tcc.dto.user.auth.RegisterUserDTO;
import com.backend.tcc.dto.user.auth.ResponseUserDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.repositories.UserRepository;
import com.backend.tcc.repositories.UserRoleRepository;
import com.backend.tcc.security.TokenService;
import com.backend.tcc.services.CloudinaryService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final CloudinaryService cloudinaryService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO credentials){
        User usuario = repository.findByLogin(credentials.login())
                            .orElseThrow(() -> new PadraoException("Usuário ou senha incorretos"));
        
        if(passwordEncoder.matches(credentials.password(), usuario.getPassword())){
            String token = this.tokenService.generateToken(usuario);
            return ResponseEntity.ok().body(new LoginResponseDTO(token, usuario.getLogin(), usuario.getRole().getRole(), usuario.getId()));
        }

        throw new PadraoException("Usuário ou senha incorretos");
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseUserDTO> register(@ModelAttribute RegisterUserDTO credentials){
        Optional<User> usuario = repository.findByLogin(credentials.login());

        if(usuario.isEmpty()){
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(credentials.password()));
            newUser.setLogin(credentials.login());
            newUser.setName(credentials.name());

            try {
                if (credentials.image() != null && !credentials.image().isEmpty()) {
                    String imageUrl = cloudinaryService.uploadFile(credentials.image());
                    newUser.setImage(imageUrl);
                }else{
                    newUser.setImage(Contants.GROUP_NOIMAGE_URL);
                }
            } catch (Exception e) {
                throw new PadraoException("Erro ao salvar imagem");
            }

            UserRole role = roleRepository.findByRole(credentials.role()).orElseThrow(() -> new PadraoException("Role não encontrada"));
            newUser.setRole(role);
            newUser = this.repository.save(newUser);
            
            return ResponseEntity.ok(new ResponseUserDTO(newUser.getId(), newUser.getName(), newUser.getLogin(), newUser.getRole()));
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/validate/{token}")
    public boolean validateToken(@PathVariable String token){
        return !tokenService.validateToken(token).isEmpty();
    }

}
