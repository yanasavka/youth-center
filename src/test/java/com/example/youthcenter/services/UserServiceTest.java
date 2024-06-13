package com.example.youthcenter.services;

import com.example.youthcenter.models.Role;
import com.example.youthcenter.models.User;
import com.example.youthcenter.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUserSuccess() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("plainpassword");

        boolean isCreated = userService.createUser(user);

        assertThat(isCreated).isTrue();
        assertThat(user.isActive()).isTrue();
        assertThat(passwordEncoder.matches("plainpassword", user.getPassword())).isTrue();
        assertThat(user.getRoles()).contains(Role.ROLE_MANAGER);

        Optional<User> savedUser = userRepository.findByEmail(user.getEmail());
        assertThat(savedUser).isPresent();
        assertThat(savedUser.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    public void testCreateUserAlreadyExists() {
        User user = new User();
        user.setEmail("existing@example.com");
        user.setPassword("plainpassword");

        userRepository.save(user);

        boolean isCreated = userService.createUser(user);

        assertThat(isCreated).isFalse();
        long userCount = userRepository.count();
        assertThat(userCount).isEqualTo(1);
    }
}

