package com.example.youthcenter.models;

import lombok.Getter;

@Getter
public enum Theme {
    FOOTBALL("Футбол"),
    VOLUNTEER("Волонтерство"),
    MOVIE("Перегляд фільмів"),
    CLEANING("Прибирання"),
    TENNIS("Гра в теніс");
    private final String theme;
    Theme(String theme) {this.theme = theme;}
}
