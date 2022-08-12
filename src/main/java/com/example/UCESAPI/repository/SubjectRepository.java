package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject,Integer> {
    List<Subject> findAllByCareer(Career career);

    @Query(value = "SELECT s.id, s.code, s.name, s.id_statistics, s.id_career FROM subjects s INNER JOIN correlatives c on s.id = c.id_correlative WHERE c.id_subject = :id",
    nativeQuery = true)
    List<Subject> getCorrelativesById(Integer id);
}
