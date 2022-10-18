package com.example.UCESAPI.model.dto.poll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PollAnsweredDto {

    Integer userId;
    List<PollAnswerDto> answers;
}
