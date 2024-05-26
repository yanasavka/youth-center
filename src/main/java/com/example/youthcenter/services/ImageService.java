package com.example.youthcenter.services;

import com.example.youthcenter.models.Image;
import com.example.youthcenter.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> findImagesByPostId(Long postId) {
        return imageRepository.findByPostId(postId);
    }
}
