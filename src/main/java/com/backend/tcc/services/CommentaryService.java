package com.backend.tcc.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.tcc.domain.commentary.Commentary;
import com.backend.tcc.domain.publish.Publish;
import com.backend.tcc.dto.commentary.CommentaryRequestDTO;
import com.backend.tcc.dto.commentary.CommentaryResponseDTO;
import com.backend.tcc.exceptions.PadraoException;
import com.backend.tcc.mapper.CommentaryMapper;
import com.backend.tcc.repositories.CommentaryRepository;
import com.backend.tcc.repositories.PublishRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentaryService {
    private final CommentaryRepository repository;
    private final CommentaryMapper mapper;
    private final PublishRepository publishRepository;

    public List<CommentaryResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public CommentaryResponseDTO findById(String id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new PadraoException("Comentário não encontrado"));
    }

    public List<CommentaryResponseDTO> findByPublishId(String id) {
        return repository.findByPublishIdOrderByWhenSentDesc(id).stream()
                .map(mapper::toDto)
                .toList();
    }

    public CommentaryResponseDTO save(CommentaryRequestDTO request) {
        Publish publish = publishRepository.findById(request.publish())
                .orElseThrow(() -> new PadraoException("Publicação não encontrada"));

        try {
            Commentary entity = mapper.toEntity(request);
            entity.setWhenSent(LocalDateTime.now());
            entity = repository.save(entity);

            publish.setQtCommentary(publish.getQtCommentary() + 1);
            publishRepository.save(publish);

            return mapper.toDto(entity);
        } catch (Exception e) {
            throw new PadraoException("Erro ao criar comentário");
        }
    }

    public CommentaryResponseDTO update(CommentaryRequestDTO request) {
        try {
            Commentary entity = repository.findById(request.id())
                    .orElseThrow(() -> new PadraoException("Comentário não encontrado"));
            entity = mapper.toEntity(request);
            return mapper.toDto(repository.save(entity));
        } catch (Exception e) {
            throw new PadraoException("Erro ao atualizar comentário");
        }
    }

    public String delete(String id) {
        try {
            repository.deleteById(id);

            return "Deletado com sucesso";
        } catch (Exception e) {
            throw new PadraoException("Erro ao deletar comentário");
        }
    }
}
