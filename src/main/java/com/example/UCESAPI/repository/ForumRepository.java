package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.Forum;
import com.example.UCESAPI.model.Query;
import com.example.UCESAPI.model.Recommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Integer> {
    Page<Query> findAllQueriesByBoard(Board board, Pageable pageable);
    Page<Recommendation> findAllRecommendationsByBoard(Board board, Pageable pageable);
    Page<Query> findAllQueriesByBoardOrderByDateDesc(Board board, Pageable pageable);
    Page<Recommendation> findAllRecommendationsByBoardOrderByDateDesc(Board board, Pageable pageable);
    @org.springframework.data.jpa.repository.Query(
            value = "SELECT f.id,f.body,f.date,f.id_user,f.id_board,f.forum_type " +
                    "FROM forums f \n" +
                    "LEFT JOIN users_voted_forums u \n" +
                    "ON f.id = u.id_forum \n" +
                    "WHERE f.id_board = :id_board AND forum_type = 1 \n" +
                    "GROUP BY f.id\n" +
                    "ORDER BY count(u.id_forum) DESC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM forums f \n" +
                    "LEFT JOIN users_voted_forums u \n" +
                    "ON f.id = u.id_forum \n" +
                    "WHERE f.id_board = :id_board AND forum_type = 1 \n" +
                    "GROUP BY f.id\n" +
                    "ORDER BY count(u.id_forum) ",
            nativeQuery = true
    )
    Page<Query> findAllQueriesOrderByUsersWhoVotedCountDesc(@Param("id_board") int id_board, Pageable pageable);

    @org.springframework.data.jpa.repository.Query(
        value = "SELECT f.id,f.body,f.date,f.id_user,f.id_board,f.forum_type " +
                "FROM forums f \n" +
                "LEFT JOIN users_voted_forums u \n" +
                "ON f.id = u.id_forum \n" +
                "WHERE f.id_board = :id_board AND forum_type = 2 \n" +
                "GROUP BY f.id\n" +
                "ORDER BY count(u.id_forum) DESC"
            ,
            countQuery = "SELECT COUNT(*)" +
                    "FROM forums f \n" +
                    "LEFT JOIN users_voted_forums u \n" +
                    "ON f.id = u.id_forum \n" +
                    "WHERE f.id_board = :id_board AND forum_type = 2 \n" +
                    "GROUP BY f.id\n",
            nativeQuery = true
    )
    Page<Recommendation> findAllRecommendationsOrderByUsersWhoVotedCountDesc(@Param("id_board") int id_board, Pageable pageable);



}
