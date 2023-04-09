package com.example.UCESAPI.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    private UserType userType;

    private boolean active;

    private boolean confirmedEmail;

    @Column(name = "u_password")
    private String password;

    private byte[] image;
    @ManyToMany(mappedBy = "usersWhoVoted", targetEntity = Forum.class)
    private List<Forum> forumsVoted;

    public boolean addVoteToForum(Forum forum)
    {
        return this.forumsVoted.add(forum);
    }
    public boolean removeVoteToForum(Forum forum)
    {
        return this.forumsVoted.remove(forum);
    }

}
