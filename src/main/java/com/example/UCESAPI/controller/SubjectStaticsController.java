package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.SubjectStaticsNotExistsException;
import com.example.UCESAPI.model.SubjectStatics;
import com.example.UCESAPI.service.SubjectStaticsService;
import com.example.UCESAPI.utils.EntityResponse;
import com.example.UCESAPI.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject/statics")
public class SubjectStaticsController {

    private final SubjectStaticsService subjectStaticsService;
    private final String SUBJECT_STATICS_PATH = "subject";

    @Autowired
    public SubjectStaticsController(SubjectStaticsService subjectStaticsService) {
        this.subjectStaticsService = subjectStaticsService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> add(SubjectStatics subjectStatics) {
        SubjectStatics subjectStaticsCreated = this.subjectStaticsService.add(subjectStatics);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(SUBJECT_STATICS_PATH, subjectStaticsCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<SubjectStatics>> getAll (@RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value ="page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page,size);
        Page<SubjectStatics> subjectStaticsPage = this.subjectStaticsService.getAll(pageable);
        return EntityResponse.listResponse(subjectStaticsPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SubjectStatics> getById(@PathVariable Integer id) throws SubjectStaticsNotExistsException {
        SubjectStatics subjectStatics = this.subjectStaticsService.getById(id);
        return ResponseEntity.ok(subjectStatics);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.subjectStaticsService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
