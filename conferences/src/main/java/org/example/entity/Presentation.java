package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode
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
