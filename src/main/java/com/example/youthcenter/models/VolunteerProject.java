package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "volunteer_projects")
public class VolunteerProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String description;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @ManyToMany(mappedBy = "volunteerProjects")
    private List<User> participants;

    public VolunteerProject() {}
}
