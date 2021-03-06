package com.example.UCESAPI.controller;

import com.example.UCESAPI.model.QueryResponse;
import com.example.UCESAPI.service.QueryResponseService;
import com.example.UCESAPI.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/query/responses/")
public class QueryResponseController {

    private final String RESPONSE_PATH = "/api/response";
    private final QueryResponseService responseService;

    @Autowired
    public QueryResponseController(QueryResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody QueryResponse response){
        QueryResponse r = this.responseService.add(response);
        return ResponseEntity.created(EntityURLBuilder.buildURL(RESPONSE_PATH, r.getId())).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id){
        this.responseService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
