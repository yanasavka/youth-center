package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "resumes")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String content;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    private JobVacancy jobVacancy;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn
    private User creator;

    @Column(updatable = false, nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    private String creatorComment;
    @Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
