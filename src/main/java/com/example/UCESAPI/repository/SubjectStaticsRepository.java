package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.SubjectStatics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectStaticsRepository extends JpaRepository<SubjectStatics, Integer> {
}
