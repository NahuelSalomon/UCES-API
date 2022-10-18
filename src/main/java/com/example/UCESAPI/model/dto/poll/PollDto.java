package com.example.UCESAPI.model.dto.poll;

import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PollDto {
    Integer id;
    String title;
    String description;
    Career career;
    Subject subject;
    List<PollQuestion> questions;
}
