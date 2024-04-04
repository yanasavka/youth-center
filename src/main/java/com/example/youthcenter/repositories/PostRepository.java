package com.example.youthcenter.repositories;

import com.example.youthcenter.models.Post;
import com.example.youthcenter.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    List<Post> findByUserId(Long userId);
}
