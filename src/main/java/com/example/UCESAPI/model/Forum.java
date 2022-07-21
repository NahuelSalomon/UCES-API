package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "forumType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Recommendation.class, name = "RECOMMENDATION"),
        @JsonSubTypes.Type(value = Query.class, name = "QUERY")
})
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Forum {

    @Id
    private Integer id;

    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name = "upvotes")
    private Integer upVotes;

    @Column(name = "downvotes")
    private Integer downVotes;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonBackReference(value = "forum-board")
    @JoinColumn(name = "id_board")
    private Board board;

    @AccessType(AccessType.Type.PROPERTY)
    public abstract ForumType forumType();
}
