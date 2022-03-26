package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@NoArgsConstructor
@Builder
@Data
@Entity
@DiscriminatorValue("0")
public class Recommendation extends Forum{

    @Override
    public ForumType forumType() {
        return ForumType.RECOMMENDATION;
    }
}
