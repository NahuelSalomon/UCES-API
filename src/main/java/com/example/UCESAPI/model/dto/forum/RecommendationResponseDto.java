package com.example.UCESAPI.model.dto.forum;

import com.example.UCESAPI.model.Board;
import com.example.UCESAPI.model.ForumType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecommendationResponseDto {
    private Integer id;
    private String body;
    private LocalDateTime date;
    private UserResponseForForumDto user;
    private List<UserResponseForForumDto> usersWhoVoted;
    private Board board;
    private ForumType forumType;
}
