package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@Entity
@Table(name = "job_vacancies")
public class JobVacancies {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private String requirements;

    @Column(updatable = false, nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column()
    private Status status;

    @JoinColumn(name = "creator_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User creator;

    @Column(nullable = false)
    private Integer views = 0;

    public JobVacancies(){}

}
