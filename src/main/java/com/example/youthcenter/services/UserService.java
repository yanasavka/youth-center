package com.example.youthcenter.services;

import com.example.youthcenter.models.MyUserDetails;
import com.example.youthcenter.models.Role;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public List<User> getUsersNonActive() {
        List<User> users = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (!user.isActive()) {
                users.add(user);
            }
        }
        return users;
    }
    public void admitUser(User user) {
        user.setActive(true);
        userRepository.save(user);
    }
    public boolean createUser(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent()) return false;
        user.setActive(false);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_MANAGER);
        userRepository.save(user);
        return true;
    }

    public Optional<User> getUserByPrincipal(Principal principal) {
        if(principal == null) return Optional.of(new User());
        return userRepository.findByEmail(principal.getName());
    }
    public MyUserDetails getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                return (MyUserDetails) principal;
            }
        }
        return null; // No user logged in
    }

    public List<User> getUsersByIds(List<Long> participantIds) {
        return userRepository.findAllById(participantIds);
    }
}
