package com.backend.tcc.domain.publish;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.backend.tcc.domain.commentary.Commentary;
import com.backend.tcc.domain.group.album.Album;
import com.backend.tcc.domain.image.Images;
import com.backend.tcc.domain.userpublish.UserPublish;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Publish {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String description;
    private LocalDateTime whenSent;
    private int qtCommentary;

    @OneToMany(mappedBy = "publish", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Images> images = new ArrayList<>();

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Commentary> commentaries;

    @OneToMany(mappedBy = "publish", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserPublish> userPublishs = new ArrayList<>();;

    @ManyToOne
    private Album album;
}
