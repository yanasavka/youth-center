package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String permission;
    public Role(){}

}
