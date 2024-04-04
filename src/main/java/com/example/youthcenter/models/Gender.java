package com.example.youthcenter.models;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("Чоловік"),
    FEMALE("Жінка");
    private final String gender;
    Gender(String gender) { this.gender = gender; }
}
