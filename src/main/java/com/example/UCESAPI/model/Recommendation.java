package com.example.UCESAPI.model;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("2")
public class Recommendation extends Forum{

    @Override
    public ForumType forumType() {
        return ForumType.RECOMMENDATION;
    }
}
