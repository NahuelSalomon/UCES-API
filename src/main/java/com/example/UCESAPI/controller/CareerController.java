package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.CareerNotFoundException;
import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.service.CareerService;
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
@RequestMapping("/api/careers")
public class CareerController {

    private final CareerService careerService;
    private static final String CAREER_PATH = "careers";

    @Autowired
    public CareerController(CareerService careerService){
        this.careerService = careerService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> add(@RequestBody Career career) {

        Career careerCreated = this.careerService.add(career);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(CAREER_PATH,careerCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Career>> getAll(@RequestParam(value = "size",defaultValue = "10") Integer size,
                                               @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Career> careers = this.careerService.getAll(pageable);
        return EntityResponse.pageResponse(careers);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Career> getById(@PathVariable Integer id) throws CareerNotFoundException {
        Career career = this.careerService.getById(id);
        return ResponseEntity.ok(career);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.careerService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
