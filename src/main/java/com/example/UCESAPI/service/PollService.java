package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.PollNotFoundException;
import com.example.UCESAPI.exception.notfound.PollQuestionNotFoundException;
import com.example.UCESAPI.mapper.PollAnswerMapper;
import com.example.UCESAPI.mapper.PollDtoMapper;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollAnswer;
import com.example.UCESAPI.model.PollQuestion;
import com.example.UCESAPI.model.dto.poll.PollAnswerDto;
import com.example.UCESAPI.model.dto.poll.PollDto;
import com.example.UCESAPI.model.dto.poll.PollAnsweredDto;
import com.example.UCESAPI.repository.PollAnswerRepository;
import com.example.UCESAPI.repository.PollQuestionRepository;
import com.example.UCESAPI.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    private final PollRepository pollRepository;
    private final PollQuestionRepository pollQuestionRepository;
    private final PollAnswerRepository pollAnswerRepository;

    @Autowired
    PollService(PollRepository pollRepository, PollQuestionRepository pollQuestionRepository, PollAnswerRepository pollAnswerRepository){
        this.pollRepository = pollRepository;
        this.pollQuestionRepository = pollQuestionRepository;
        this.pollAnswerRepository = pollAnswerRepository;
    }

    public PollDto getPollById(Integer pollId) throws PollNotFoundException {
        List<PollQuestion> questions;
        Optional<Poll> poll = pollRepository.findById(pollId);
        if (poll.isEmpty()){
            throw new PollNotFoundException();
        }
        questions = pollQuestionRepository.findAllByPollTemplate(poll.get().getPollTemplate());
        return PollDtoMapper.map(poll.get(), questions);
    }

    public PollDto getPollByCareerId(Integer careerId) throws PollNotFoundException {
        List<PollQuestion> questions;
        Optional<Poll> poll = pollRepository.findByCareerId(careerId);
        if (poll.isEmpty()){
            throw new PollNotFoundException();
        }
        questions = pollQuestionRepository.findAllByPollTemplate(poll.get().getPollTemplate());
        return PollDtoMapper.map(poll.get(), questions);
    }

    public PollDto getPollBySubjectId(Integer subjectId) throws PollNotFoundException {
        List<PollQuestion> questions;
        Optional<Poll> poll = pollRepository.findBySubjectId(subjectId);
        if(poll.isEmpty()){
            throw new PollNotFoundException();
        }
        questions = pollQuestionRepository.findAllByPollTemplate(poll.get().getPollTemplate());
        return PollDtoMapper.map(poll.get(),questions);
    }


    public void processPollAnswers(Integer idPoll, PollAnsweredDto pollAnswered) throws PollQuestionNotFoundException{
        for (PollAnswerDto answer : pollAnswered.getAnswers()) {
            Optional<PollQuestion> question = pollQuestionRepository.findById(answer.getQuestionId());
            if (question.isEmpty()) {
                throw new PollQuestionNotFoundException();
            }
            PollAnswer answerMapped = PollAnswerMapper.map(answer, question.get());
            if (isValidAnswer(answerMapped)) {
                pollAnswerRepository.save(answerMapped);
            }
        }
        pollRepository.saveUserWithPollAnswered(pollAnswered.getUserId(), idPoll);
    }

    private Boolean isValidAnswer(PollAnswer answer) {
        switch (answer.getQuestion().getPollResponseType()){
            case SHORT_ANSWER:
            case SHORT_NUMBER_ANSWER:
                return answer.getShortAnswer() != null;
            case RATING_TO_FIVE:
                return answer.getRating() != null;
            case PROFESSOR_RATING:
                return answer.getRating() != null && answer.getProfessor() != null;
            default:
                return false;
        }
    }
}
