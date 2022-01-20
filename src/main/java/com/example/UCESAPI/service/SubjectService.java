package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.ProfessorNotExistsException;
import com.example.UCESAPI.exception.SubjectNotExistsException;
import com.example.UCESAPI.model.Professor;
import com.example.UCESAPI.model.Subject;
import com.example.UCESAPI.repository.ProfessorRepository;
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

    public Subject getById(Integer id) throws SubjectNotExistsException {
        return this.subjectRepository.findById(id).orElseThrow(SubjectNotExistsException::new);
    }

    public void deleteById(Integer id) {
        this.subjectRepository.deleteById(id);
    }

    public void update(Integer id, Subject subjectUpdated) throws SubjectNotExistsException {
        this.getById(id);
        this.subjectRepository.save(subjectUpdated);
    }

}
