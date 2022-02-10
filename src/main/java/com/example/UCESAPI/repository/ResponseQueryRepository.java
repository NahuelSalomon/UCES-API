package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.ResponseQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseQueryRepository extends JpaRepository<ResponseQuery, Integer> {

    Page<ResponseQuery> findResponseQueriesByQueryId(Integer idQuery, Pageable p);

}
