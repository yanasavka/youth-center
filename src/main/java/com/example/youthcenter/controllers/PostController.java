package com.example.youthcenter.controllers;

import com.example.youthcenter.models.Image;
import com.example.youthcenter.models.Permission;
import com.example.youthcenter.models.Post;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.PostRepository;
import com.example.youthcenter.repositories.UserRepository;
import com.example.youthcenter.services.ImageService;
import com.example.youthcenter.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
     private final PostService postService;
     private final PostRepository postRepository;
     private final UserRepository userRepository;
     private final ImageService imageService;

    @ModelAttribute("permissions")
    public Permission[] getPermissions() {
        return Permission.values();
    }

    @GetMapping("/my/users/{userId}/posts")
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String getUserPosts(@PathVariable(value = "userId") Long userId, Model model) {
        Optional<User> userOptional = userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            List<Post> posts = postRepository.findByUserId(userId);
            model.addAttribute("user", user);
            model.addAttribute("posts", posts);
        });
        return "for-participants/user-posts";
    }
    // не чіпати
    @GetMapping("/my/users/{userId}/post/add")
    public String addPostForm(@PathVariable(value = "userId") Long userId, Model model, Principal principal){
        model.addAttribute("userId", userId);
        model.addAttribute("user", principal.getName());
        return "post-add";
    }
    // не чіпати
    @PostMapping("/my/users/{userId}/post/add")
    public String postAdding(@PathVariable(value = "userId") Long userId,
                             Post post, Principal principal,
                             @RequestParam("file") MultipartFile[] files,
                             Model model) throws IOException {
        model.addAttribute("userId", userId);
//        Optional<User> user = userRepository.findById(userId);
//        if(user.isPresent()){
//            User res = user.get();
//            post.setUser(res);
//        }
        if (files != null && files.length > 0 && files.length <= 10) {
            postService.addPost(principal, post, files);
        }
        return "redirect:/my/users";
    }

    @GetMapping("/my/posts/{postId}/edit")
    public String editPostForm(Model model, @PathVariable(value = "postId") Long postId){
        Optional<Post> postOptional = postRepository.findById(postId);
        postOptional.ifPresent(post -> {
            List<Image> imageList = post.getImages();
            model.addAttribute("post", post);
            model.addAttribute("images", imageList);
        });
        return "post-edit";
    }
//    @PostMapping("/my/posts/{postId}/edit")
//    public RedirectView editPost(@PathVariable(value = "postId") Long postId,
//                                 @RequestParam(value = "file", required = false) MultipartFile[] files,
//                                 Post post,
//                                 //@RequestParam String content, @RequestParam String type,
//                                 RedirectAttributes redirectAttributes) throws IOException{
//
////        User user = postRepository.findById(postId).get().getUser();
////        post.setUser(user);
////        post.setContent(content);
////        Permission permission = Permission.valueOf(type.toUpperCase());
////        post.setType(permission);
//        postService.editPost(post);
//        Long userId = post.getUser().getId();
//        redirectAttributes.addAttribute("userId", userId);
//        RedirectView rv = new RedirectView();
//        rv.setContextRelative(true);
//        rv.setUrl("/my/users/{userId}/posts");
//        return rv;
//    }
    // працює
    @PostMapping("/my/posts/{postId}/edit")
    public RedirectView editPost(@PathVariable(value = "postId") Long postId,
                                 @RequestParam(value = "file", required = false) MultipartFile[] files,
                                 @Validated Post post,
                                 RedirectAttributes redirectAttributes,Model model) throws IOException {
        model.addAttribute("post", post);
        postService.editPost(post);

        if (files != null && files.length > 0) {
            List<Image> images = new ArrayList<>();
            for (MultipartFile file : files) {
                Image image = new Image();
                images.add(image);
            }
            post.setImages(images);
        }
        redirectAttributes.addAttribute("userId", post.getUser().getId());
        RedirectView rv = new RedirectView();
        rv.setContextRelative(true);
        rv.setUrl("/my/users/{userId}/posts");
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

//    @PostMapping("/my/posts/{postId}/like")
//    public ResponseEntity<LikeResponse> likePost(@PathVariable Long postId, Principal principal) {
//        LikeResponse response = postService.likePost(postId, (User) principal);
//        if (response.isSuccess()) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

}
