package com.example.youthcenter.services;

import com.example.youthcenter.models.MyUserDetails;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
//    public MyUserDetailsService(){}
//    @Autowired
//    public MyUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(email + " not found"));
    }
}
