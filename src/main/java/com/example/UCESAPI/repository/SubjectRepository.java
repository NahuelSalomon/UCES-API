package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    List<Subject> findAllByCareer(Career career);
}
