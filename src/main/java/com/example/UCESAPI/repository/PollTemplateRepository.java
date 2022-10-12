package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.PollTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollTemplateRepository extends JpaRepository<PollTemplate, Integer> {
}
