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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;

    //@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(
            name = "users_voted_forums",
            joinColumns = {@JoinColumn(name = "id_forum")},
            inverseJoinColumns = {@JoinColumn(name = "id_user")}
    )
    private List<User> usersWhoVoted;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonBackReference(value = "forum-board")
    @JoinColumn(name = "id_board")
    private Board board;
    
    private LocalDateTime date;

    @AccessType(value = AccessType.Type.PROPERTY)
    public abstract ForumType forumType();



    public boolean addUserWhoVoted(User user)
    {
        return this.usersWhoVoted.add(user);
    }
    public boolean removeUserWhoVoted(User user)
    {
        return this.usersWhoVoted.remove(user);
    }



}
