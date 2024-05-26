package com.example.youthcenter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "posts")
@AllArgsConstructor
@DynamicUpdate
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

    @JoinColumn(name = "user_id", updatable = false)
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "post")
    private List<Image> images = new ArrayList<>();

    private Long previewImageId;

//    @OneToMany
//    private Set<User> likedBy = new HashSet<>();

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


