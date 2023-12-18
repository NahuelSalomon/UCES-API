package com.example.UCESAPI.controller;

import com.example.UCESAPI.exception.AccessNotAllowedException;
import com.example.UCESAPI.model.QueryResponse;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.service.QueryResponseService;
import com.example.UCESAPI.service.UserService;
import com.example.UCESAPI.utils.EntityURLBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/query/responses/")
public class QueryResponseController {

    private final String RESPONSE_PATH = "/api/response";
    private final QueryResponseService responseService;
    private final UserService userService;

    @Autowired
    public QueryResponseController(QueryResponseService responseService, UserService userService) {
        this.responseService = responseService;
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize(value ="hasRole('ROLE_STUDENT')")
    public ResponseEntity<Object> add(@RequestBody QueryResponse response, Authentication authentication) throws AccessNotAllowedException {
        User authenticatedUser = this.userService.getByEmail(( (UserDetails) authentication.getPrincipal()).getUsername());

        if(!authenticatedUser.getId().equals(response.getUser().getId()))
        {
            throw new AccessNotAllowedException("You have not access to this resource");
        }

        QueryResponse r = this.responseService.add(response);
        return ResponseEntity.created(EntityURLBuilder.buildURL(RESPONSE_PATH, r.getId())).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value ="hasRole('ROLE_ADMINISTRATOR')")
    public ResponseEntity<Object> delete(@PathVariable Integer id){
        this.responseService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
