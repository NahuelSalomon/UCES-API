package com.example.UCESAPI.utils;

import com.example.UCESAPI.model.Query;
import com.example.UCESAPI.model.Recommendation;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.model.dto.forum.QueryResponseDto;
import com.example.UCESAPI.model.dto.forum.QueryResponseResponseDto;
import com.example.UCESAPI.model.dto.forum.RecommendationResponseDto;
import com.example.UCESAPI.model.dto.forum.UserResponseForForumDto;
import com.example.UCESAPI.model.dto.user.ForumResponseForUserDto;
import com.example.UCESAPI.model.dto.user.UserResponseDto;

import java.util.stream.Collectors;

public class CustomConversion {

    public static UserResponseDto UserToUserResponseDto(User user)
    {
        return UserResponseDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .id(user.getId())
                .userType(user.getUserType())
                .email(user.getEmail())
                .active(user.isActive())
                .confirmedEmail(user.isConfirmedEmail())
                .forumsVoted(
                        user.getForumsVoted().stream().map(
                                forum -> ForumResponseForUserDto
                                        .builder()
                                        .id(forum.getId())
                                        .body(forum.getBody())
                                        .board(forum.getBoard())
                                        .forumType(forum.forumType())
                                        .build()
                        ).collect(Collectors.toList()))
                .build();
    }

    public static RecommendationResponseDto RecommendationToRecommendationResponseDto(Recommendation recommendation)
    {
        return RecommendationResponseDto.builder()
                                        .id(recommendation.getId())
                                        .body(recommendation.getBody())
                                        .user(UserResponseForForumDto.builder()
                                                                     .id(recommendation.getUser().getId())
                                                                     .firstname(recommendation.getUser().getFirstname())
                                                                     .lastname(recommendation.getUser().getLastname())
                                                                     .id(recommendation.getUser().getId())
                                                                     .userType(recommendation.getUser().getUserType())
                                                                     .email(recommendation.getUser().getEmail())
                                                                     .active(recommendation.getUser().isActive())
                                                                     .confirmedEmail(recommendation.getUser().isConfirmedEmail())
                                                                     .build())
                                        .usersWhoVoted(
                                                recommendation.getUsersWhoVoted().stream().map(
                                                        user -> UserResponseForForumDto.builder()
                                                                .id(recommendation.getUser().getId())
                                                                .firstname(recommendation.getUser().getFirstname())
                                                                .lastname(recommendation.getUser().getLastname())
                                                                .id(recommendation.getUser().getId())
                                                                .userType(recommendation.getUser().getUserType())
                                                                .email(recommendation.getUser().getEmail())
                                                                .active(recommendation.getUser().isActive())
                                                                .confirmedEmail(recommendation.getUser().isConfirmedEmail())
                                                                .build()
                                                ).collect(Collectors.toList())
                                        )
                                        .board(recommendation.getBoard())
                                        .forumType(recommendation.forumType())
                                        .build();
    }

    public static QueryResponseDto QueryToQueryResponseDto(Query query)
    {
        return QueryResponseDto.builder()
                .id(query.getId())
                .body(query.getBody())
                .user(UserResponseForForumDto.builder()
                        .id(query.getUser().getId())
                        .firstname(query.getUser().getFirstname())
                        .lastname(query.getUser().getLastname())
                        .id(query.getUser().getId())
                        .userType(query.getUser().getUserType())
                        .email(query.getUser().getEmail())
                        .active(query.getUser().isActive())
                        .confirmedEmail(query.getUser().isConfirmedEmail())
                        .build())
                .usersWhoVoted(
                        query.getUsersWhoVoted().stream().map(
                                user -> UserResponseForForumDto.builder()
                                        .id(query.getUser().getId())
                                        .firstname(query.getUser().getFirstname())
                                        .lastname(query.getUser().getLastname())
                                        .id(query.getUser().getId())
                                        .userType(query.getUser().getUserType())
                                        .email(query.getUser().getEmail())
                                        .active(query.getUser().isActive())
                                        .confirmedEmail(query.getUser().isConfirmedEmail())
                                        .build()
                        ).collect(Collectors.toList())
                )
                .board(query.getBoard())
                .forumType(query.forumType())
                .responses(query.getResponses().stream().map(
                            queryResponse -> QueryResponseResponseDto.builder()
                                                .id(queryResponse.getId())
                                                .body(queryResponse.getBody())
                                                .user(UserResponseForForumDto.builder()
                                                        .id(queryResponse.getUser().getId())
                                                        .firstname(queryResponse.getUser().getFirstname())
                                                        .lastname(queryResponse.getUser().getLastname())
                                                        .id(queryResponse.getUser().getId())
                                                        .userType(queryResponse.getUser().getUserType())
                                                        .email(queryResponse.getUser().getEmail())
                                                        .active(queryResponse.getUser().isActive())
                                                        .confirmedEmail(queryResponse.getUser().isConfirmedEmail())
                                                        .build())
                                                .build()
                            ).collect(Collectors.toList()))
                .build();
    }
}
