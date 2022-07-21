package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.ResponseQueryNotFoundException;
import com.example.UCESAPI.model.QueryResponse;
import com.example.UCESAPI.repository.ResponseQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class QueryResponseService {

    private final ResponseQueryRepository responseRepository;

    @Autowired
    public QueryResponseService(ResponseQueryRepository responseQueryRepository) {
        this.responseRepository = responseQueryRepository;
    }

    public QueryResponse add(QueryResponse response) {
        return this.responseRepository.save(response);
    }

    public Page<QueryResponse> getAllByQuery(Integer idQuery, Pageable p){
        return this.responseRepository.findResponseQueriesByQueryId(idQuery, p);
    }

    public QueryResponse getById(Integer id) throws ResponseQueryNotFoundException {
        return responseRepository.findById(id).orElseThrow(ResponseQueryNotFoundException::new);
    }

    public void delete(Integer id){
        this.responseRepository.deleteById(id);
    }
}


