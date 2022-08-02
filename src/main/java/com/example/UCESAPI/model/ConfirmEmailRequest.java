package com.example.UCESAPI.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ConfirmEmailRequest {

    @Email
    private String email;
    private String urlToRedirect;

}
