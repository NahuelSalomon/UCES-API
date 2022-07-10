package com.example.UCESAPI.repository;

import com.example.UCESAPI.exception.model.QueryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseQueryRepository extends JpaRepository<QueryResponse, Integer> {

    Page<QueryResponse> findResponseQueriesByQueryId(Integer idQuery, Pageable p);

}
