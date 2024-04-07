package com.example.youthcenter.controllers;

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
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
     private final PostService postService;
     private final PostRepository postRepository;
     private final UserRepository userRepository;

    @ModelAttribute("permissions")
    public Permission[] getPermissions() {
        return Permission.values();
    }

    @GetMapping("/my/users/{userId}/posts")
    public String getUserPosts(@PathVariable(value = "userId") Long userId, Model model) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            List<Post> posts = postRepository.findByUserId(userId);
            model.addAttribute("user", user);
            model.addAttribute("posts", posts);
        });
        return "user-posts";
    }
    @GetMapping("/my/users/{userId}/post/add")
    public String addPostForm(@PathVariable(value = "userId") Long userId, Model model){
        model.addAttribute("userId", userId);
        return "post-add";
    }

    @PostMapping("/my/users/{userId}/post/add")
    public String postAdding(@PathVariable(value = "userId") Long userId,
                             Post post,
                             @RequestParam("file") MultipartFile[] files,
                             Model model) throws IOException {
        model.addAttribute("userId", userId);
        Optional<User> user = userRepository.findById(userId);

        if(user.isPresent()){
            User res = user.get();
            post.setUser(res);
        }
        if (files != null && files.length > 0 && files.length <= 10) {
            postService.addPost(post, files);
        }

        return "redirect:/users";
    }
    @GetMapping("/my/posts/{postId}/edit")
    public String editPostForm(Model model, @PathVariable(value = "postId") Long postId){
        Optional<Post> postOptional = postRepository.findById(postId);
        Post post = postOptional.get();
        model.addAttribute("post", post);
        return "post-edit";
    }
    @PostMapping("/posts/{postId}/edit")
    public RedirectView editPost(@PathVariable(value = "postId") Long postId,
                                 @RequestParam String content, @RequestParam String type,
                                 RedirectAttributes redirectAttributes){
        Post post = postRepository.findById(postId).orElseThrow();
        post.setContent(content);
        Permission permission = Permission.valueOf(type.toUpperCase());
        post.setType(permission);
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);
        Long userId = post.getUser().getId();
        redirectAttributes.addAttribute("userId", userId);
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/users/{userId}/posts");
        return rv;
    }
    @PostMapping("/my/posts/{postId}/remove")
    public RedirectView deletePost(@PathVariable(value = "postId") Long postId,
                                   RedirectAttributes redirectAttributes){
        Post post = postRepository.findById(postId).orElseThrow();
        postRepository.delete(post);
        Long userId = post.getUser().getId();
        redirectAttributes.addAttribute("userId", userId);
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/my/users/{userId}/posts");
        return rv;
    }

}
