package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.PollUserNotFoundException;
import com.example.UCESAPI.model.Poll;
import com.example.UCESAPI.model.PollUser;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.repository.PollUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PollUserService {

    private final PollUserRepository pollUserRepository;

    @Autowired
    public PollUserService(PollUserRepository pollUserRepository) {
        this.pollUserRepository = pollUserRepository;
    }

    public PollUser add(PollUser pollUser) {
        return this.pollUserRepository.save(pollUser);
    }

    public PollUser getByPollAndUser(Poll poll, User user) {
        return this.pollUserRepository.findByPollAndUser(poll, user);
    }

    public Page<PollUser> getAll(Pageable pageable) {
        return this.pollUserRepository.findAll(pageable);
    }

    public PollUser getById(Integer id) throws PollUserNotFoundException {
        return this.pollUserRepository.findById(id).orElseThrow(PollUserNotFoundException::new);
    }

    public void deleteById(Integer id) {
        this.pollUserRepository.deleteById(id);
    }


}
