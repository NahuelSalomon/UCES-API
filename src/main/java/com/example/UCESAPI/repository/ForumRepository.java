package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Integer> {
    List<Forum> findAllByBoard(Board board);
}
