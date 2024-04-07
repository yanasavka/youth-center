package com.example.youthcenter.controllers;

import com.example.youthcenter.models.Gender;
import com.example.youthcenter.models.Permission;
import com.example.youthcenter.models.Post;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.PostRepository;
import com.example.youthcenter.repositories.UserRepository;
import com.example.youthcenter.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostService postService;

    @GetMapping("/home")
    public String home(Model model){
        return "home";
    }

}
