package com.backend.tcc.domain.group;

import java.util.ArrayList;
import java.util.List;

import com.backend.tcc.domain.group.album.Album;
import com.backend.tcc.domain.groupinvite.GroupInvite;
import com.backend.tcc.domain.user.User;
import com.backend.tcc.domain.usergroup.UserGroup;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

@Entity(name="people_group")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id; 
    private String name;
    private String description;
    private String image_name;
    private String image;

    @ManyToOne
    private User adm;

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<UserGroup> userGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Album> albums;

    @JsonIgnore
    @OneToMany(mappedBy = "group", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<GroupInvite> groupInvites = new ArrayList<>(); // AD
}
