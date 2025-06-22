package com.backend.tcc.domain.usergroup;

import java.time.LocalTime;

import com.backend.tcc.domain.group.Group;
import com.backend.tcc.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JsonIgnore
    private User user;
    
    @ManyToOne
    @JsonIgnore
    private Group group;

    private Integer totalNotifies = 0;
    private LocalTime hourLastPublish;
}
