package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.SubjectStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectStatisticsRepository extends JpaRepository<SubjectStatistics, Integer> {
}
