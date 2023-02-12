package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "polls_x_users")
public class PollUser {

    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_poll")
    private Poll poll;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
}
