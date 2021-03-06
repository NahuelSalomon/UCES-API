package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Board findBySubject(Subject subject);
}
