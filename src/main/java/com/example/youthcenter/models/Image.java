package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.*;
@Data
@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String originalFileName;

    private Long size;

    private String contentType;

    private boolean isPreviewImage;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] bytes;

    @ManyToOne
    private Post post;

    public Image(){}
}
