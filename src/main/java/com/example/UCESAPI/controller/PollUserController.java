package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.exception.notfound.PollUserNotFoundException;
import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.mapper.PollUserMapper;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollUser;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.dto.poll.PollUserDto;
import com.example.UCESAPI.repository.PollRepository;
import com.example.UCESAPI.service.PollUserService;
import com.example.UCESAPI.service.UserService;
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
@RequestMapping("/api/pollUser")
public class PollUserController {


    private final PollUserService pollUserService;
    private final PollRepository pollRepository;
    private final UserService userService;
    private final String POLL_USER_PATH = "pollUser";

    @Autowired
    public PollUserController(PollUserService pollUserService, PollRepository pollRepository, UserService userService) {
        this.pollUserService = pollUserService;
        this.pollRepository = pollRepository;
        this.userService = userService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<Object> add(@RequestBody PollUser pollUser) {
        PollUser pollUserCreated = this.pollUserService.add(pollUser);
        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .location(EntityURLBuilder.buildURL(POLL_USER_PATH,pollUserCreated.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .build();
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<PollUser>> getAll(@RequestParam(value = "size", defaultValue = "10") Integer size, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        Pageable pageable = PageRequest.of(page,size);
        Page<PollUser> pollUserPage = this.pollUserService.getAll(pageable);
        return EntityResponse.pageResponse(pollUserPage);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PollUser> getById(@PathVariable Integer id) throws PollUserNotFoundException {
        PollUser pollUser = this.pollUserService.getById(id);
        return ResponseEntity.ok(pollUser);
    }

    @GetMapping(value = "poll/{idPoll}/user/{idUser}")
    public ResponseEntity<PollUserDto> getByPollAndUser(@PathVariable Integer idPoll, @PathVariable Integer idUser) throws PollNotFoundException, UserNotFoundException {
        Poll poll = this.pollRepository.findById(idPoll).orElseThrow(PollNotFoundException::new);
        User user = this.userService.getById(idUser);
        PollUser pollUser = this.pollUserService.getByPollAndUser(poll,user);
        PollUserDto pollUserDto = pollUser != null ? PollUserMapper.map(pollUser) : null;
        return ResponseEntity.ok(pollUserDto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable Integer id) {
        this.pollUserService.deleteById(id);
        return ResponseEntity.accepted().build();
    }

}
