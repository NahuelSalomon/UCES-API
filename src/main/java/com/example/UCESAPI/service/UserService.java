package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.UserNotFoundException;
import com.example.UCESAPI.model.User;
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

    public User getById(Integer id) throws UserNotFoundException {
        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void deleteById(Integer id) {
        this.userRepository.deleteById(id);
    }

    public void update(Integer id, User user) throws UserNotFoundException {
        this.getById(id);
        this.userRepository.save(user);
    }

    public User login(String email, String password) {
        return this.userRepository.findByEmailAndPassword(email, password);

    }
}
