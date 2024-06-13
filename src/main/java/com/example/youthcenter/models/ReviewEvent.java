package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class ReviewEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Event event;

    private URL url;

    public ReviewEvent() {}

}
