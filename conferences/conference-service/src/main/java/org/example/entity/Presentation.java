package org.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Presentation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @OneToOne(mappedBy = "presentation")
    private Schedule schedule;

    @ManyToMany(mappedBy = "presenterPresentations", fetch = FetchType.LAZY)
    private Set<User> presenters = new HashSet<>();

    @ManyToMany(mappedBy = "listenerPresentations", fetch = FetchType.LAZY)
    private Set<User> listeners = new HashSet<>();
}
