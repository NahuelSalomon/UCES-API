package com.example.UCESAPI.controller;

import com.example.UCESAPI.model.Query;
import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.exception.notfound.ForumNotFoundException;
import com.example.UCESAPI.model.Forum;
import com.example.UCESAPI.model.Recommendation;
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
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/forums")
public class ForumController {

    final private ForumService forumService;
    //final private ResponseQueryService responseService;
    final private String FORUM_PATH = "/api/forums";

    @Autowired
    public ForumController(ForumService forumService){
        this.forumService = forumService;
    }

    @PostMapping
    public ResponseEntity<Forum> add(@RequestBody Forum forum){
        Forum newForum = forumService.addForum(forum);
        return ResponseEntity.created(EntityURLBuilder.buildURL(FORUM_PATH, newForum.getId())).build();
    }

    @GetMapping("/queries")
    public ResponseEntity<Page<Forum>> getAllQueries(Pageable pageable){
        Page<Forum> page = forumService.getAll(pageable);
        return ResponseEntityMaker.response(page.getContent(), page);
    }

    @GetMapping("queries/boards/{idBoard}")
    public ResponseEntity<Page<Query>> getAllQueriesByBoard(@PathVariable int idBoard, Pageable pageable) throws BoardNotFoundException {
        Page<Query> forumList = forumService.getAllQueriesByBoard(idBoard, pageable);
        return ResponseEntity.ok(forumList);
    }

    @GetMapping("recommendations/boards/{idBoard}")
    public ResponseEntity<Page<Recommendation>> getAllRecommendationsByBoard(@PathVariable int idBoard, Pageable pageable) throws BoardNotFoundException {
        Page<Recommendation> forumList = forumService.getAllRecommendationsByBoard(idBoard, pageable);
        return ResponseEntity.ok(forumList);
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
