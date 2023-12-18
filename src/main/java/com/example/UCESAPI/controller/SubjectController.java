package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.CareerNotFoundException;
import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.Subject;
import com.example.UCESAPI.service.BoardService;
import com.example.UCESAPI.service.SubjectService;
import com.example.UCESAPI.utils.EntityResponse;
import com.example.UCESAPI.utils.EntityURLBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/subjects")
public class    SubjectController {

    private final SubjectService subjectService;
    private final BoardService boardService;
    private final String SUBJECT_PATH = "subject";

    public SubjectController(SubjectService subjectService, BoardService boardService) {
        this.subjectService = subjectService;
        this.boardService = boardService;
    }

    @PostMapping(value = "/")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Subject> add(@RequestBody Subject subject) {
        Subject subjectCreated = this.subjectService.add(subject);
        Board boardToCreate = new Board(0,subjectCreated.getName(),subjectCreated);
        Board boardCreated = this.boardService.addBoard(boardToCreate);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(SUBJECT_PATH, subjectCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(subjectCreated);
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

    @GetMapping(value = "/search")
    public ResponseEntity<Subject> getByNameAndCareerId(
            @RequestParam("name") String name,
            @RequestParam("idCareer") int idCareer
    ) throws SubjectNotFoundException {
        Subject subject = this.subjectService.getByNameAndCareerId(name, idCareer);
        return ResponseEntity.ok(subject);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.subjectService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
