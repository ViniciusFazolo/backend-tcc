package com.backend.tcc.domain.group.album;

import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.backend.tcc.domain.group.Group;
import com.backend.tcc.domain.publish.Publish;

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
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "image")
    @JdbcTypeCode(SqlTypes.VARBINARY)
    private byte[] image;
    private String name;

    @ManyToOne
    private Group group;

    @OneToMany(mappedBy = "album")
    private List<Publish> publishs;
}
