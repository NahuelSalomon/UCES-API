package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.ProfessorNotFoundException;
import com.example.UCESAPI.model.Professor;
import com.example.UCESAPI.service.ProfessorService;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/professor")
public class ProfessorController {

    private final ProfessorService professorService;
    private final String PROFESSOR_PATH = "professor";

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> add(@RequestBody Professor professor) {
        Professor professorCreated = this.professorService.add(professor);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(PROFESSOR_PATH,professorCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Professor>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page,size);
        Page<Professor> professorPage = this.professorService.getAll(pageable);
        return EntityResponse.pageResponse(professorPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Professor> getById(@PathVariable Integer id) throws ProfessorNotFoundException {
        Professor professor = this.professorService.getById(id);
        return ResponseEntity.ok(professor);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.professorService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
