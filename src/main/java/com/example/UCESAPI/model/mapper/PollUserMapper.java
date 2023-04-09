package com.example.UCESAPI.model.mapper;
import com.example.UCESAPI.model.PollUser;
import com.example.UCESAPI.model.dto.poll.PollUserDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PollUserMapper {

    public static PollUserDto map(PollUser pollUser){
        return PollUserDto.builder()
                .id(pollUser.getId())
                .build();
    }

}
