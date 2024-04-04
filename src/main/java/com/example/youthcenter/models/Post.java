package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Permission type;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer totalLikes = 0;

    @Column(nullable = false)
    private Integer totalComments = 0;

    @Column(updatable = false, nullable = false, columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt;

    @Column(columnDefinition = "timestamp null default current_timestamp on update current_timestamp")
    private LocalDateTime updatedAt;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.REFRESH)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Image> images = new ArrayList<>();

    private Long previewImageId;
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public void setTotalComments(){
        this.totalComments = comments.size();
    }

    public void addImageToPost(Image image){
        image.setPost(this);
        images.add(image);
    }
    public Post() {}
}
