package com.example.youthcenter.repositories;

import com.example.youthcenter.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    List<User> findByName(String name);
    List<User> findByNameOrSurname(String name, String surname);
    List<User> findByFullNameContainingIgnoreCase(String fullName);
    Optional<User> findByEmail(String email);
}
