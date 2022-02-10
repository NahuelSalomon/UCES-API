package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.ResponseQueryNotExistsException;
import com.example.UCESAPI.model.ResponseQuery;
import com.example.UCESAPI.model.Subject;
import com.example.UCESAPI.repository.ResponseQueryRepository;
import com.example.UCESAPI.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class ResponseQueryService {

    private final ResponseQueryRepository responseRepository;

    @Autowired
    public ResponseQueryService(ResponseQueryRepository responseQueryRepository) {
        this.responseRepository = responseQueryRepository;
    }

    public ResponseQuery add(ResponseQuery response) {
        return this.responseRepository.save(response);
    }

    public Page<ResponseQuery> getAllByQuery(Integer idQuery, Pageable p){
        return this.responseRepository.findResponseQueriesByQueryId(idQuery, p);
    }

    public ResponseQuery getById(Integer id) throws ResponseQueryNotExistsException {
        return responseRepository.findById(id).orElseThrow(ResponseQueryNotExistsException::new);
    }

    public ResponseEntity delete(Integer id){
        this.responseRepository.deleteById(id);
    }
}


