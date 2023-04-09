package com.example.UCESAPI.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDto {

    @NotBlank(message = "Firstname is required.")
    private String firstname;

    @NotBlank(message = "Lastname is required.")
    private String lastname;

    @NotBlank(message = "Email is required.")
    private String email;

    private String image;
}
