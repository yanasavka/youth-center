package com.example.youthcenter.models;

import lombok.Getter;

@Getter
public enum Permission {
    PRIVATE ("Доступний тільки мені"),
    PUBLIC ("Доступний усім"),
    RESTRICTED ("Доступний певним користувачам");
    private final String type;
    Permission(String type) {
        this.type = type;
    }
}
