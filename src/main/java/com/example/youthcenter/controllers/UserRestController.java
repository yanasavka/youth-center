package com.example.youthcenter.controllers;

import com.example.youthcenter.models.MyUserDetails;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.UserRepository;
import com.example.youthcenter.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {

    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserRestController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/other-users")
    public List<User> getOtherUsers() {
        MyUserDetails loggedInUser = userService.getLoggedInUser();
        List<User> allUsers = userRepository.findAll();
        allUsers.removeIf(user -> user.getEmail().equals(loggedInUser.getUsername())); // Remove logged-in user
        return allUsers;
    }
}
