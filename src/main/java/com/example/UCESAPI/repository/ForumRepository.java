package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.Forum;
import com.example.UCESAPI.model.Query;
import com.example.UCESAPI.model.Recommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Integer> {

    Page<Query> findAllQueriesByBoard(Board board, Pageable pageable);
    Page<Recommendation> findAllRecommendationsByBoard(Board board, Pageable pageable);

}
