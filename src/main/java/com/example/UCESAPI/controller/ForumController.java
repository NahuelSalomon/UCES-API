package com.example.UCESAPI.controller;

import com.example.UCESAPI.model.Query;
import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.exception.notfound.ForumNotFoundException;
import com.example.UCESAPI.model.Forum;
import com.example.UCESAPI.model.Recommendation;
import com.example.UCESAPI.model.dto.forum.QueryResponseDto;
import com.example.UCESAPI.model.dto.forum.RecommendationResponseDto;
import com.example.UCESAPI.service.ForumService;
import com.example.UCESAPI.utils.CustomConversion;
import com.example.UCESAPI.utils.EntityURLBuilder;
import com.example.UCESAPI.utils.ResponseEntityMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/")
    public ResponseEntity<List<Forum>> getAll(Pageable pageable) throws BoardNotFoundException {
        Page<Forum> queryPage = forumService.getAll(pageable);
        return ResponseEntityMaker.paginatedListResponse(queryPage);
    }

    @GetMapping("queries/boards/{idBoard}")
    public ResponseEntity<List<QueryResponseDto>> getAllQueriesByBoard(@PathVariable int idBoard, Pageable pageable) throws BoardNotFoundException {
        Page<Query> queryPage = forumService.getAllQueriesByBoard(idBoard, pageable);
        Page<QueryResponseDto> queryResponseDtoPage = queryPage.map(CustomConversion::QueryToQueryResponseDto);
        return ResponseEntityMaker.paginatedListResponse(queryResponseDtoPage);
    }

    @GetMapping("queries/boards/{idBoard}/sort/votes")
    public ResponseEntity<List<QueryResponseDto>> getAllQueriesByBoardSortedByVotes(@PathVariable int idBoard, Pageable pageable) throws BoardNotFoundException {
        Page<Query> queryPage = forumService.getAllQueriesByBoardSortedByVotes(idBoard, pageable);
        Page<QueryResponseDto> queryResponseDtoPage = queryPage.map(CustomConversion::QueryToQueryResponseDto);
        return ResponseEntityMaker.paginatedListResponse(queryResponseDtoPage);
    }

    @GetMapping("queries/boards/{idBoard}/sort/date")
    public ResponseEntity<List<QueryResponseDto>> getAllQueriesByBoardSortedByDates(@PathVariable int idBoard, Pageable pageable) throws BoardNotFoundException {
        Page<Query> queryPage = forumService.getAllQueriesByBoardSortedByDate(idBoard, pageable);
        Page<QueryResponseDto> queryResponseDtoPage = queryPage.map(CustomConversion::QueryToQueryResponseDto);
        return ResponseEntityMaker.paginatedListResponse(queryResponseDtoPage);
    }

    @GetMapping("recommendations/boards/{idBoard}")
    public ResponseEntity<List<RecommendationResponseDto>> getAllRecommendationsByBoard(@PathVariable int idBoard, Pageable pageable) throws BoardNotFoundException {
        Page<Recommendation> recommendationPage = forumService.getAllRecommendationsByBoard(idBoard, pageable);
        Page<RecommendationResponseDto> recommendationResponseDtos = recommendationPage.map(CustomConversion::RecommendationToRecommendationResponseDto);
        return ResponseEntityMaker.paginatedListResponse(recommendationResponseDtos);
    }

    @GetMapping("recommendations/boards/{idBoard}/sort/votes")
    public ResponseEntity<List<RecommendationResponseDto>> getAllRecommendationsByBoardSortedByVotes(@PathVariable int idBoard, Pageable pageable) throws BoardNotFoundException {
        Page<Recommendation> recommendationPage = forumService.getAllRecommendationsByBoardSortedByVotes(idBoard, pageable);
        Page<RecommendationResponseDto> recommendationResponseDtos = recommendationPage.map(CustomConversion::RecommendationToRecommendationResponseDto);
        return ResponseEntityMaker.paginatedListResponse(recommendationResponseDtos);
    }

    @GetMapping("recommendations/boards/{idBoard}/sort/date")
    public ResponseEntity<List<RecommendationResponseDto>> getAllRecommendationsByBoardSortedByDates(@PathVariable int idBoard, Pageable pageable) throws BoardNotFoundException {
        Page<Recommendation> recommendationPage = forumService.getAllRecommendationsByBoardSortedByDate(idBoard, pageable);
        Page<RecommendationResponseDto> recommendationResponseDtos = recommendationPage.map(CustomConversion::RecommendationToRecommendationResponseDto);
        return ResponseEntityMaker.paginatedListResponse(recommendationResponseDtos);
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
