package com.example.UCESAPI.model.mapper;

import com.example.UCESAPI.model.PollAnswer;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.dto.poll.PollAnswerDto;
import org.springframework.stereotype.Component;

@Component
public class PollAnswerMapper {

    public static PollAnswer map(PollAnswerDto answerDto, PollQuestion question){
        return PollAnswer.builder()
                .shortAnswer(answerDto.getShortAnswer())
                .professor(answerDto.getProfessor())
                .pollQuestion(question)
                .rating(answerDto.getRating())
                .build();
    }
}
