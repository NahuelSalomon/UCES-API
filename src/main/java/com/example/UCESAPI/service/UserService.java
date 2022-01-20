package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.SubjectStaticsNotExistsException;
import com.example.UCESAPI.exception.UserNotExistsException;
import com.example.UCESAPI.model.SubjectStatics;
import com.example.UCESAPI.model.User;
import com.example.UCESAPI.repository.SubjectStaticsRepository;
import com.example.UCESAPI.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User add(User user) {
        return this.userRepository.save(user);
    }

    public Page<User> getAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    public User getById(Integer id) throws UserNotExistsException {
        return this.userRepository.findById(id).orElseThrow(UserNotExistsException::new);
    }

    public void deleteById(Integer id) {
        this.userRepository.deleteById(id);
    }

    public void update(Integer id, User user) throws UserNotExistsException {
        this.getById(id);
        this.userRepository.save(user);
    }

}
