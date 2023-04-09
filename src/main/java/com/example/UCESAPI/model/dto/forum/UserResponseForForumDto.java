package com.example.UCESAPI.model.dto.forum;

import com.example.UCESAPI.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseForForumDto {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private UserType userType;
    private boolean active;
    private boolean confirmedEmail;
    private byte[] image;
}
