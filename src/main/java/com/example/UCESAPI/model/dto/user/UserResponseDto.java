package com.example.UCESAPI.model.dto.user;

import com.example.UCESAPI.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDto {

    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private UserType userType;
    private byte[] image;
    private boolean active;
    private boolean confirmedEmail;
    private List<ForumResponseForUserDto> forumsVoted;

}
