package com.example.youthcenter.services;

import com.example.youthcenter.models.Image;
import com.example.youthcenter.models.Post;
import com.example.youthcenter.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
public class PostService {
    private final PostRepository postRepository;
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void addPost(Post post, MultipartFile[] imageFiles) throws IOException {

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
