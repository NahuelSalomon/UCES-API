package com.example.UCESAPI.model.dto.poll;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PollAnswerDto {

    Integer rating;
    String shortAnswer;
    Integer questionId;
}
