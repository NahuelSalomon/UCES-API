package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Query extends Forum{

    private List<ResponseQuery> responses;


    @Override
    public ForumType forumType() {
        return ForumType.QUERY;
    }
}
