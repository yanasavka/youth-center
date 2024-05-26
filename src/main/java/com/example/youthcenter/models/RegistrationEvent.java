package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "registrations_for_event")
public class RegistrationEvent {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn
    private Event event;
    @ManyToOne
    @JoinColumn
    private User participant;

    public RegistrationEvent() {}
}
