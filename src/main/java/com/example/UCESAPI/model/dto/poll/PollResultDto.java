package com.example.UCESAPI.model.dto.poll;

import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.dto.user.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PollResultDto {
    private long id;
    private Poll poll;
    private UserResponseDto studentUser;
}
