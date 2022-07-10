package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.model.ForumType;
import com.example.UCESAPI.exception.model.Query;
import com.example.UCESAPI.exception.notfound.ForumNotFoundException;
import com.example.UCESAPI.exception.model.Forum;
import com.example.UCESAPI.service.ForumService;
import com.example.UCESAPI.utils.EntityURLBuilder;
import com.example.UCESAPI.utils.ResponseEntityMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/forums")
public class ForumController {

    final private ForumService forumService;
    //final private ResponseQueryService responseService;
    final private String FORUM_PATH = "/api/forums";

    @Autowired
    public ForumController(ForumService forumService){
        this.forumService = forumService;
        //this.responseService = responseService;
    }

    @PostMapping
    public ResponseEntity<Forum> addForum(@RequestBody Forum forum){
        Forum newForum = forumService.addForum(forum);
        return ResponseEntity.created(EntityURLBuilder.buildURL(FORUM_PATH, newForum.getId())).build();
    }

    @PostMapping("/queries/")
    public ResponseEntity<Forum> addQueries(@RequestBody Query query){
        Forum newForum = forumService.addForum(query);
        return ResponseEntity.created(EntityURLBuilder.buildURL(FORUM_PATH, newForum.getId())).build();
    }



    @GetMapping
    public ResponseEntity<Page<Forum>> getAll(Pageable pageable){
        Page<Forum> page = forumService.getAll(pageable);
        return ResponseEntityMaker.response(page.getContent(), page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Forum> getById(@PathVariable Integer id) throws ForumNotFoundException {
        Forum forum = forumService.getById(id);
        return ResponseEntity.ok(forum);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteForum(@PathVariable Integer id) throws ForumNotFoundException {
        forumService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateForum(@PathVariable Integer id, @RequestBody Forum newForum) throws ForumNotFoundException {
        forumService.update(id, newForum);
        return ResponseEntity.accepted().build();
    }

//    @GetMapping("/{idForum}/response")
//    public ResponseEntity<Page<ResponseQuery>> getResponsesByQuery(@PathVariable Integer idQuery, Pageable pageable)
//    {
//        Page<ResponseQuery> resp = responseService.getAllByQuery(idQuery,pageable);
//        return ResponseEntityMaker.response(resp.getContent(), resp);
//    }
}
