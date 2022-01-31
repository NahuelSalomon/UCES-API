package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.SubjectStaticsNotExistsException;
import com.example.UCESAPI.model.SubjectStatistics;
import com.example.UCESAPI.service.SubjectStatisticsService;
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
public class SubjectStatisticsController {

    private final SubjectStatisticsService subjectStatisticsService;
    private final String SUBJECT_STATICS_PATH = "subject";

    @Autowired
    public SubjectStatisticsController(SubjectStatisticsService subjectStatisticsService) {
        this.subjectStatisticsService = subjectStatisticsService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> add(SubjectStatistics subjectStatistics) {
        SubjectStatistics subjectStatisticsCreated = this.subjectStatisticsService.add(subjectStatistics);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(SUBJECT_STATICS_PATH, subjectStatisticsCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<SubjectStatistics>> getAll (@RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value ="page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page,size);
        Page<SubjectStatistics> subjectStaticsPage = this.subjectStatisticsService.getAll(pageable);
        return EntityResponse.listResponse(subjectStaticsPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SubjectStatistics> getById(@PathVariable Integer id) throws SubjectStaticsNotExistsException {
        SubjectStatistics subjectStatistics = this.subjectStatisticsService.getById(id);
        return ResponseEntity.ok(subjectStatistics);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.subjectStatisticsService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
