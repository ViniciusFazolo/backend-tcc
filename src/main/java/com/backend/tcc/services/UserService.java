package com.backend.tcc.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.tcc.domain.user.User;
import com.backend.tcc.domain.user.UserRole;
import com.backend.tcc.dto.user.UserRequestDTO;
import com.backend.tcc.dto.user.UserResponseDTO;
import com.backend.tcc.dto.user.auth.RegisterUserDTO;
import com.backend.tcc.dto.user.auth.ResponseUserDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.mapper.UserMapper;
import com.backend.tcc.repositories.GroupRepository;
import com.backend.tcc.repositories.UserRepository;
import com.backend.tcc.repositories.UserRoleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final UserRoleRepository roleRepository;
    private final UserMapper mapper;
    private final GroupRepository groupRepository;

    public List<UserResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }
    
    public ResponseEntity<List<UserResponseDTO>> findByGroupId(String id) {
        groupRepository.findById(id).orElseThrow(() -> new PadraoException("Grupo não encontrado"));
        List<UserResponseDTO> objs = repository.findByGroupId(id).stream().map(mapper::toDto).toList();

        return ResponseEntity.ok().body(objs);
    }

    public UserResponseDTO findById(String id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new PadraoException("Usuário não encontrado"));
    }

    public boolean existsByLogin(String login) {
        return repository.existsByLogin(login);
    }

    public ResponseEntity<ResponseUserDTO> save(RegisterUserDTO request) {
        try {
            User obj = new User();
            obj.setName(request.name());
            obj.setLogin(request.login());
            obj.setPassword(passwordEncoder.encode(request.password()));
           
            UserRole role = roleRepository.findById(request.role()).orElseThrow(() -> new PadraoException("Role não encontrada"));
            obj.setRole(role);

            repository.save(obj);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDTO(obj));
        } catch (DataIntegrityViolationException e) {
            throw new PadraoException("Este e-mail está em uso");
        } catch (Exception e){
            throw new PadraoException("Erro ao salvar registro");
        }
    }

    public ResponseEntity<ResponseUserDTO> update(UserRequestDTO request){
        try {
            User obj = repository.findById(request.id()).orElseThrow(() -> new PadraoException(request.id()));
            obj.setName(request.name());
            obj.setLogin(request.login());

            if(request.password() != null){
                obj.setPassword(passwordEncoder.encode(request.password()));
            }

            UserRole role = roleRepository.findById(request.roleId()).orElseThrow(() -> new PadraoException("Role não encontrada"));
            obj.setRole(role);

            repository.save(obj);

            return ResponseEntity.ok().body(mapper.toDTO(obj));
        } catch (Exception e){
            throw new PadraoException("Erro ao atualizar registro. Tente novamente");
        }
    }

    public ResponseEntity<String> delete(String id) {
        try {
            User obj = repository.findById(id)
                    .orElseThrow(() -> new PadraoException("Usuário não encontrado"));
            repository.delete(obj);
            return ResponseEntity.ok().body("Registro excluído com sucesso.");
        } catch (Exception e) {
            throw new PadraoException("Erro ao deletar usuário");
        }
    }
}
