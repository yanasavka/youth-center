package com.example.youthcenter.repositories;

import com.example.youthcenter.models.VolunteerProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerProjectRepository extends JpaRepository<VolunteerProject, Long> {
    List<VolunteerProject> findAllByNameStartingWithIgnoreCase(String title);
}
