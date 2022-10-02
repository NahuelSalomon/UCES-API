package com.example.UCESAPI.model.dto.forum;

import com.example.UCESAPI.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class QueryResponseResponseDto {
    private Integer id;
    private String body;
    private UserResponseForForumDto user;
}
