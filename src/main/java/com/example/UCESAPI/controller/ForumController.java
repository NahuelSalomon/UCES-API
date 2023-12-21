package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.model.Query;
import com.example.UCESAPI.exception.notfound.BoardNotFoundException;
import com.example.UCESAPI.exception.notfound.ForumNotFoundException;
import com.example.UCESAPI.model.Forum;
import com.example.UCESAPI.model.Recommendation;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.dto.forum.QueryResponseDto;
import com.example.UCESAPI.model.dto.forum.RecommendationResponseDto;
import com.example.UCESAPI.service.ForumService;
import com.example.UCESAPI.model.mapper.CustomConversion;
import com.example.UCESAPI.service.UserService;
import com.example.UCESAPI.utils.EntityURLBuilder;
import com.example.UCESAPI.utils.ResponseEntityMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/forums")
public class ForumController {

    private final ForumService forumService;
    private final UserService userService;
    private final String FORUM_PATH = "/api/forums";

    @Autowired
    public ForumController(ForumService forumService, UserService userService){
        this.forumService = forumService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize(value ="hasRole('ROLE_STUDENT' )")
    public ResponseEntity<Forum> add(@RequestBody Forum forum, Authentication authentication) throws AccessNotAllowedException {
        User authenticatedUser = this.userService.getByEmail(( (UserDetails) authentication.getPrincipal()).getUsername());
        if(!authenticatedUser.getId().equals(forum.getUser().getId()))
        {
            throw new AccessNotAllowedException("You have not access to this resource");
        }

        Forum newForum = forumService.addForum(forum);
        return ResponseEntity.created(UriComponentsBuilder.fromPath("/api/careers/{id}")
                .buildAndExpand(newForum.getId())
                .toUri()).build();
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
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR' )")
    public ResponseEntity<Object> deleteForum(@PathVariable Integer id) throws ForumNotFoundException {
        forumService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
