package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity(name = "users")
@DiscriminatorColumn(
        name = "type_user",
        discriminatorType = DiscriminatorType.INTEGER
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String firstname;

    private String lastname;

    private String email;

    @Column(name = "type_user")
    private TypeUser typeUser;

    @Column(name = "u_password")
    private String password;

}
