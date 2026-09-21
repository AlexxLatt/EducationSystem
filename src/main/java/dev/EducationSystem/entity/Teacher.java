package dev.EducationSystem.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "teachers")
public class Teacher {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;


    @JsonIgnore
    @OneToMany(
            mappedBy = "teacher",
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Schedule> schedules = new ArrayList<>();
}
