package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Builder
@Data
@Entity
public class Recommendation extends Forum{

    public Recommendation() {

    }

    @Override
    public ForumType forumType() {
        return ForumType.RECOMMENDATION;
    }
}
