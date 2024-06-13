package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime deadline;
    @JoinColumn
    @ManyToOne
    private User sender;

    @ManyToOne
    @JoinColumn
    private User assignedTo;

    @Column(updatable = false, nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "timestamp null default current_timestamp on update current_timestamp")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn
    private VolunteerProject volunteerProject;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public Task() {}

}
