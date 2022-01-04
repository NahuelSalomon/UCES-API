package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "users")
public class User {

    @Id
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

}
