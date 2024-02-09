package com.example.UCESAPI.utils;

import com.example.UCESAPI.model.*;
import com.example.UCESAPI.model.dto.poll.PollResultDto;
import com.example.UCESAPI.model.dto.user.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelTestUtil {

    public static Forum someForum() {
        return new Recommendation();
    }

    public static Page<Forum> somePageOfForums() {
        List<Forum> forumList = Collections.singletonList(new Recommendation());
        return new PageImpl<>(forumList);
    }

    public static Pageable somePageable() {
        return Pageable.ofSize(1);
    }

    public static Page<Query> somePageOfQueries() {

        List<Query> queryList = Collections.singletonList(someQuery());
        return new PageImpl<>(queryList);
    }

    public static Page<Recommendation> somePageOfRecommendations() {
        List<Recommendation> recommendationList = Collections.singletonList(someRecommendation());
        return new PageImpl<>(recommendationList);
    }

    public static Page<Forum> somePageOfForum() {
        List<Forum> recommendationList = Collections.singletonList(someRecommendation());
        return new PageImpl<>(recommendationList);
    }

    public static com.example.UCESAPI.model.User someUser()
    {
        return com.example.UCESAPI.model.User.builder()
                                        .firstname("user")
                                        .lastname("user")
                                        .email("user@gmail.com")
                                        .password("pws").id(1)
                                        .userType(UserType.ROLE_STUDENT)
                                        .forumsVoted(new ArrayList<>()).build();
    }
    public static UserResponseDto someStudentUserDto()
    {
        return UserResponseDto.builder().email("user@gmail.com").id(1).build();
    }



    public static QueryResponse someQueryResponse()
    {
        QueryResponse queryResponse = new QueryResponse();
        queryResponse.setUser(someUser());
        return queryResponse;
    }

    public static Query someQuery()
    {
        Query query = new Query();
        query.setUser(someUser());
        query.setUsersWhoVoted(List.of(someUser()));
        query.setResponses(List.of(someQueryResponse()));
        return query;
    }

    public static Recommendation someRecommendation()
    {
        Recommendation recommendation = new Recommendation();
        recommendation.setUser(someUser());
        recommendation.setUsersWhoVoted(List.of(someUser()));
        return recommendation;
    }

    public static List<PollAnswer> somePollAnswerList()
    {
        return List.of(somePollAnswer());
    }

    public static PollAnswer somePollAnswer()
    {
        PollAnswer pollAnswer = new PollAnswer();
        pollAnswer.setPollResult(somePollResult());
        pollAnswer.setPollQuestion(somePollQuestion());
        pollAnswer.setBoolResponse(true);
        pollAnswer.setRankResponse(1);
        return pollAnswer;
    }

    public static PollQuestionStatistic somePollQuestionStatistic()
    {
        return PollQuestionStatistic.builder()
                .id(1L)
                .pollQuestion(somePollQuestion())
                .NumberOfResponses(2)
                .NumberOfNegativeResponse(1)
                .NumberOfPositiveResponse(1)
                .TotalRangeResponse(2)
                .build();
    }

    public static PollQuestion somePollQuestion()
    {
        return PollQuestion.builder()
                .id(1)
                .poll(somePoll())
                .question("Some question")
                .pollResponseType(PollResponseType.YES_NO_ANSWER)
                .shortDescription("Shor description")
                .build();
    }

    public static PollResult somePollResult()
    {
        PollResult pollResult = new PollResult();
        pollResult.setId(1L);
        pollResult.setStudentUser(someUser());
        pollResult.setPoll(somePoll());
        return pollResult;
    }

    public static PollResultDto somePollResultDto()
    {
        PollResultDto pollResultDto = new PollResultDto();
        pollResultDto.setId(1);
        pollResultDto.setPoll(somePoll());
        pollResultDto.setStudentUser(someStudentUserDto());
        return pollResultDto;
    }

    public static Poll somePoll()
    {
        Poll poll = new Poll();
        return poll;
    }

    public static Authentication someAuthentication()
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        User authenticatedUser = new User("user@gmail.com", "pws", authorities);
        return new UsernamePasswordAuthenticationToken(authenticatedUser, null);
    }

}
