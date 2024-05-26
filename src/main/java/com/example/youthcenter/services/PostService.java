package com.example.youthcenter.services;

import com.example.youthcenter.models.Image;
import com.example.youthcenter.models.Post;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.PostRepository;
import com.example.youthcenter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    public Post getPostById(Long id){
        return postRepository.findById(id).orElse(null);
    }
    public void addPost(Principal principal,
                        Post post, MultipartFile[] imageFiles) throws IOException {
        post.setUser(getUserByPrincipal(principal).orElse(null));
        Image[] images = new Image[10];
        for (int i = 0; i < imageFiles.length; i++){
            if(imageFiles[i].getSize() != 0){
                images[i] = toImageEntity(imageFiles[i]);
                post.addImageToPost(images[i]);
            }
        }

        images[0].setPreviewImage(true);

        Post postFromDB = postRepository.save(post);
        postFromDB.setPreviewImageId(postFromDB.getImages().get(0).getId());
        postRepository.save(post);
    }

    public Optional<User> getUserByPrincipal(Principal principal) {
        if(principal == null) return Optional.of(new User());
        return userRepository.findByEmail(principal.getName());
    }

    public void editPost(Post post){
        post.setUpdatedAt(LocalDateTime.now());
        postRepository.save(post);
    }

//    public LikeResponse likePost(Long postId, User user) {
//        Optional<Post> postOptional = postRepository.findById(postId);
//        if (postOptional.isPresent()) {
//            Post post = postOptional.get();
//            // Check if user has already liked the post
//            if (post.getLikedBy().contains(user)) {
//                return new LikeResponse(false, "You already liked this post.");
//            }
//            post.setTotalLikes(post.getTotalLikes() + 1);
//
//            // Update likedBy list (assuming a Set or List)
//            post.getLikedBy().add(user);
//
//            // Persist the updated post to the database
//            postRepository.save(post);
//
//            return new LikeResponse(true, post.getTotalLikes());
//        } else {
//            return new LikeResponse(false, "Post not found.");
//        }
//    }
    private Image toImageEntity(MultipartFile multipartFile) throws IOException {
        Image image = new Image();
        if(!multipartFile.isEmpty()){
            image.setName(multipartFile.getName());
            image.setContentType(multipartFile.getContentType());
            image.setOriginalFileName(multipartFile.getOriginalFilename());
            image.setSize(multipartFile.getSize());
            image.setBytes(multipartFile.getBytes());
        }
        return image;
    }
}
