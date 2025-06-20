package com.backend.tcc.domain.publish;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.backend.tcc.domain.commentary.Commentary;
import com.backend.tcc.domain.group.album.Album;
import com.backend.tcc.domain.userpublish.UserPublish;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image")
    @JdbcTypeCode(SqlTypes.VARBINARY)
    private List<byte[]> images;

    @OneToMany
    private List<Commentary> commentaries;

    @OneToMany(mappedBy = "publish")
    private List<UserPublish> userPublishs = new ArrayList<>();;

    @ManyToOne
    private Album album;
}
