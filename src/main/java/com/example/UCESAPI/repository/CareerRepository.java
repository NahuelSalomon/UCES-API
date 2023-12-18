package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CareerRepository extends JpaRepository<Career,Integer> {
    @Query("SELECT c FROM careers c WHERE LOWER(c.name) = LOWER(:name)")
    Optional<Career> findByNameIgnoreCase(@Param("name") String name);
}
