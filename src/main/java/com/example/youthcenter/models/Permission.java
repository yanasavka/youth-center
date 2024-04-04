package com.example.youthcenter.models;

import lombok.Getter;

@Getter
public enum Permission {
    PUBLIC ("Доступний усім"),
    RESTRICTED ("Доступний певним користувачам"),
    PRIVATE ("Доступний тільки мені");
    private final String type;
    Permission(String type) {
        this.type = type;
    }
}
