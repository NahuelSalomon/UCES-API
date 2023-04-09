package com.example.UCESAPI.model.mapper;

import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.dto.poll.PollDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PollDtoMapper {

    public static PollDto map(Poll poll, List<PollQuestion> questions){
        return PollDto.builder()
                .id(poll.getId())
                .career(poll.getCareer())
                .subject(poll.getSubject())
                .title(poll.getPollTemplate().getTitle())
                .description(poll.getPollTemplate().getPollDescription())
                .questions(questions)
                .build();
    }
}
