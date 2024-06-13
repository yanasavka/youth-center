package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @Column(updatable = false, nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "timestamp null default current_timestamp on update current_timestamp")
    private LocalDateTime updatedAt;
    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToMany
    @JoinTable(
            name = "participants",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<User> participants = new ArrayList<>();
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public VolunteerProject() {}
}
