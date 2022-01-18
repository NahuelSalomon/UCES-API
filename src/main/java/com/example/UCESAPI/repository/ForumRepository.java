package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.Forum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumRepository extends JpaRepository<Forum, Integer> {
}
