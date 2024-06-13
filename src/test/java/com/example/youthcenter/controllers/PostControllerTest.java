package com.example.youthcenter.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Optional;

import com.example.youthcenter.models.Post;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.UserRepository;
import com.example.youthcenter.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

public class PostControllerTest {
    private MockMvc mockMvc;
    @Mock
    private PostService postService;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private PostController postController;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
    }
    @Test
    public void testPostAdding() throws Exception {
        Principal principal = () -> "user@example.com";
        User user = new User();
        user.setId(1L);
        user.setEmail("user@example.com");
        Post post = new Post();
        MultipartFile[] files = { new MockMultipartFile("file", "file1.jpg", "image/jpeg", new byte[0]),
                new MockMultipartFile("file", "file2.jpg", "image/jpeg", new byte[0])
        };
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        mockMvc.perform(multipart("/my/users/post/add")
                        .file((MockMultipartFile) files[0])
                        .file((MockMultipartFile) files[1])
                        .principal(principal)
                        .flashAttr("post", post))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/my/users"));
        verify(postService).addPost(any(Principal.class), any(Post.class), any(MultipartFile[].class));
    }
}
