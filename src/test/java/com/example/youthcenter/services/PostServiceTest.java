package com.example.youthcenter.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

import com.example.youthcenter.models.Post;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.PostRepository;
import com.example.youthcenter.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

public class PostServiceTest {
    @Mock
    private PostRepository postRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PostService postService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddPost() throws IOException {
        Principal principal = () -> "user@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");

        Post post = new Post();
        MockMultipartFile file1 = new MockMultipartFile("file", "file1.jpg", "image/jpeg", "image content".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("file", "file2.jpg", "image/jpeg", "image content".getBytes());
        MultipartFile[] files = {file1, file2};

        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(postRepository.save(any(Post.class))).thenAnswer(invocation -> invocation.getArgument(0));

        postService.addPost(principal, post, files);

        verify(postRepository).save(any(Post.class));
    }
}

