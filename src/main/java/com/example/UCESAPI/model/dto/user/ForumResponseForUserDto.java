package com.example.UCESAPI.model.dto.user;

import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.ForumType;
import com.example.UCESAPI.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ForumResponseForUserDto {

    private Integer id;
    private String body;
    private Board board;
    private ForumType forumType;

}
