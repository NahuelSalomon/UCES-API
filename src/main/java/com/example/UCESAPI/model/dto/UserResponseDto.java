package com.example.UCESAPI.model.dto;

import com.example.UCESAPI.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {


    private int id;

    @NotBlank(message = "Firstname is required.")
    private String firstname;

    @NotBlank(message = "Lastname is required.")
    private String lastname;

    @NotBlank(message = "Email is required.")
    private String email;

    @NotBlank(message = "Email is required.")
    private UserType userType;

    private boolean active;

}
