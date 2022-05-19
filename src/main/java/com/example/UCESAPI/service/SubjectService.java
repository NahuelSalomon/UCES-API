package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.model.Subject;
import com.example.UCESAPI.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository) {
        this.subjectRepository = subjectRepository;
    }

    public Subject add(Subject subject) {
        return this.subjectRepository.save(subject);
    }

    public Page<Subject> getAll(Pageable pageable) {
        return this.subjectRepository.findAll(pageable);
    }

    public Subject getById(Integer id) throws SubjectNotFoundException {
        return this.subjectRepository.findById(id).orElseThrow(SubjectNotFoundException::new);
    }

    public void deleteById(Integer id) {
        this.subjectRepository.deleteById(id);
    }

    public void update(Integer id, Subject subjectUpdated) throws SubjectNotFoundException {
        this.getById(id);
        this.subjectRepository.save(subjectUpdated);
    }

}
