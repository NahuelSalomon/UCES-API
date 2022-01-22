package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.CareerStatisticsNotExistsException;
import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.model.CareerStatistics;
import com.example.UCESAPI.service.CareerStaticsService;
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
@RequestMapping("/api/career/statics")
public class CareerStaticsController {

    private final CareerStaticsService careerStaticsService;
    private final static String CAREER_STATICS_PATH = "career/statics";

    @Autowired
    public CareerStaticsController(CareerStaticsService careerStaticsService) {
        this.careerStaticsService = careerStaticsService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> add(@RequestBody CareerStatistics careerStatistics) {
        CareerStatistics careerStatisticsCreated = this.careerStaticsService.add(careerStatistics);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(CAREER_STATICS_PATH,careerStatisticsCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<CareerStatistics>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size,
                                                         @RequestParam(value = "size", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page,size);
        Page<CareerStatistics> careerStatisticsPage = this.careerStaticsService.getAll(pageable);
        return EntityResponse.listResponse(careerStatisticsPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CareerStatistics> getById(@PathVariable Integer id) throws CareerStatisticsNotExistsException {
        CareerStatistics careerStatistics = this.careerStaticsService.getById(id);
        return ResponseEntity.ok(careerStatistics);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.careerStaticsService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
