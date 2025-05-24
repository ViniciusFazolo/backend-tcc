package com.backend.tcc.domain.commentary;

import java.time.LocalDateTime;

import com.backend.tcc.domain.publish.Publish;
import com.backend.tcc.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commentary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime whenSent;
    
    @ManyToOne
    private User author;

    @ManyToOne
    private Publish publish;
}
