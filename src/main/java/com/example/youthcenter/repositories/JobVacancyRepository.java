package com.example.youthcenter.repositories;

import com.example.youthcenter.models.JobVacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobVacancyRepository extends JpaRepository<JobVacancy, Long> {
}
