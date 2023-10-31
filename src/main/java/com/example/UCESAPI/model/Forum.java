package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.springframework.data.annotation.AccessType;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "forums")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "forumType", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Recommendation.class, name = "RECOMMENDATION"),
        @JsonSubTypes.Type(value = Query.class, name = "QUERY")
})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="forum_type",
        discriminatorType = DiscriminatorType.INTEGER)
public abstract class Forum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String body;

    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private Board board;

    @AccessType(value = AccessType.Type.PROPERTY)
    public abstract ForumType forumType();
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(
            name = "users_voted_forums",
            joinColumns = {@JoinColumn(name = "forum_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> usersWhoVoted;

    public boolean addUserWhoVoted(User user)
    {
        return this.usersWhoVoted.add(user);
    }
    public boolean removeUserWhoVoted(User user)
    {
        return this.usersWhoVoted.remove(user);
    }



}
