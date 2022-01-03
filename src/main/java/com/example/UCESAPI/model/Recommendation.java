package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Recommendation extends Forum{

    @Override
    public ForumType forumType() {
        return ForumType.RECOMMENDATION;
    }
}
