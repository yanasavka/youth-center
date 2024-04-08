package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String fullName;

    private boolean active;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    private Image avatar;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column()
    private LocalDate dateOfBirth;

    @Column()
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column()
    private Gender gender;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    @CollectionTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

//    @JoinColumn(name = "role_id")
//    @ManyToOne
//    private Role role;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @PrePersist
    public void calculateFields() {
        this.fullName = name + " " + surname;
        if (dateOfBirth != null) {
            LocalDate currentDate = LocalDate.now();
            this.age = Period.between(dateOfBirth, currentDate).getYears();
        } else {
            this.age = 0;
        }
    }
    public User() {}
//    @PrePersist
//    private void calculateAge() {
//
//    }

}
