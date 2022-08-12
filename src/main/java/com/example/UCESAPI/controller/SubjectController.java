package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.CareerNotFoundException;
import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.model.Subject;
import com.example.UCESAPI.service.SubjectService;
import com.example.UCESAPI.utils.EntityResponse;
import com.example.UCESAPI.utils.EntityURLBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/subjects")
public class    SubjectController {

    private final SubjectService subjectService;
    private final String SUBJECT_PATH = "subject";

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> add(@RequestBody Subject subject) {
        Subject subjectCreated = this.subjectService.add(subject);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(SUBJECT_PATH, subjectCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Subject>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value ="page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Subject> subjectPage = this.subjectService.getAll(pageable);
        return EntityResponse.pageResponse(subjectPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Subject> getById(@PathVariable Integer id) throws SubjectNotFoundException {
        return ResponseEntity.ok(this.subjectService.getById(id));
    }

    @GetMapping(value = "/careers/{idCareer}")
    public ResponseEntity<List<Subject>> getByCareer(@PathVariable int idCareer) throws CareerNotFoundException {
        List<Subject> subjectList = this.subjectService.getByCareer(idCareer);
        return  EntityResponse.listResponse(subjectList);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.subjectService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(value = "/{id}/correlatives")
    public ResponseEntity<List<Subject>> getCorrelativesById(@PathVariable Integer id){
        return EntityResponse.listResponse(this.subjectService.getCorrelativesById(id));
    }



}
