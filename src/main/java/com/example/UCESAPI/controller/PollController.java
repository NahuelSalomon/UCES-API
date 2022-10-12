package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.model.dto.PollDto;
import com.example.UCESAPI.service.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/poll")
public class PollController {

    PollService pollService;

    @Autowired
    PollController(PollService pollService){
        this.pollService = pollService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PollDto> getPollById(@PathVariable Integer id) throws PollNotFoundException {
        return ResponseEntity.ok(pollService.getPollById(id));
    }
}
