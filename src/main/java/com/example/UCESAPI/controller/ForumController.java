package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.BoardNotExistsException;
import com.example.UCESAPI.exception.ForumNotExistsException;
import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.Forum;
import com.example.UCESAPI.model.ResponseQuery;
import com.example.UCESAPI.service.BoardService;
import com.example.UCESAPI.service.ForumService;
import com.example.UCESAPI.utils.EntityURLBuilder;
import com.example.UCESAPI.utils.ResponseEntityMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forum")
public class ForumController {

    final private ForumService forumService;
    final private String FORUM_PATH = "/api/forum";

    @Autowired
    public ForumController(ForumService forumService){
        this.forumService = forumService;
    }

    @PostMapping
    public ResponseEntity<Forum> addForum(@RequestBody Forum forum){
        Forum newForum = forumService.addForum(forum);
        return ResponseEntity.created(EntityURLBuilder.buildURL(FORUM_PATH, newForum.getId())).build();
    }

    @GetMapping
    public ResponseEntity<List<Forum>> getAll(Pageable pageable){
        Page page = forumService.getAll(pageable);
        return ResponseEntityMaker.response(page.getContent(), page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Forum> getById(@PathVariable Integer id) throws ForumNotExistsException {
        Forum forum = forumService.getById(id);
        return ResponseEntity.ok(forum);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteForum(@PathVariable Integer id) throws ForumNotExistsException {
        forumService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateForum(@PathVariable Integer id, @RequestBody Forum newForum) throws ForumNotExistsException {
        forumService.update(id, newForum);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{id}/addresponse")
    public ResponseEntity addResponseToQuery(@PathVariable Integer id, @RequestBody ResponseQuery response) throws ForumNotExistsException {
        forumService.addResponse(id, response);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{idForum}/deleteresponse/{idResponse}")
    public ResponseEntity deleteResponseToQuery(@PathVariable Integer idForum, @PathVariable Integer idResponse) throws ForumNotExistsException {
        forumService.deleteResponse(idForum, idResponse);
        return ResponseEntity.ok().build();
    }
}
