package com.example.youthcenter.services;

import lombok.Data;

@Data
public class LikeResponse {
    private boolean success;
    private String message;
    private int totalLikes; // Optional if you don't want to send this in every response

    public LikeResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public LikeResponse(boolean success, int totalLikes) {
        this.success = success;
        this.totalLikes = totalLikes;
    }


}

