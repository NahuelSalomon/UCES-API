package com.example.UCESAPI.exception.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Builder
@Data
@Entity(name = "recommendations")
//@DiscriminatorValue("0")
public class Recommendation extends Forum{

    @Override
    public ForumType forumType() {
        return ForumType.RECOMMENDATION;
    }
}
