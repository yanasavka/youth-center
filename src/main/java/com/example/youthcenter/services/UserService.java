package com.example.youthcenter.services;

import com.example.youthcenter.models.Role;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public boolean createUser(User user) {
        if(userRepository.findByEmail(user.getEmail()) != null) return false;
        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        return true;
    }
}
