package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "forumType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Recommendation.class, name = "RECOMMENDATION"),
        @JsonSubTypes.Type(value = Query.class, name = "QUERY")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String body;
    //private User user;
    private Integer upvotes;
    private Integer downvotes;

    @AccessType(AccessType.Type.PROPERTY)
    public abstract ForumType forumType();
}
