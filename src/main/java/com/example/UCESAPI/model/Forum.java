package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Entity(name = "forums")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "forumType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Recommendation.class, name = "RECOMMENDATION"),
        @JsonSubTypes.Type(value = Query.class, name = "QUERY")
})
@DiscriminatorColumn(
        name = "forum_type",
        discriminatorType = DiscriminatorType.STRING
)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String body;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "upvotes")
    private Integer upVotes;

    @Column(name = "downvotes")
    private Integer downVotes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference(value = "forum-board")
    @JoinColumn(name = "board_id")
    private Board board;

    @AccessType(AccessType.Type.PROPERTY)
    public abstract ForumType forumType();
}
