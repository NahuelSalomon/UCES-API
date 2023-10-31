package com.example.UCESAPI.service;

import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.exception.notfound.CareerNotFoundException;
import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.model.Subject;
import com.example.UCESAPI.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final CareerService careerService;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository, CareerService careerService) {
        this.subjectRepository = subjectRepository;
        this.careerService = careerService;
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

    public List<Subject> getByCareer(Integer idCareer) throws CareerNotFoundException {
        Career career = this.careerService.getById(idCareer);
        return this.subjectRepository.findAllByCareer(career);
    }

    public void deleteById(Integer id) {
        this.subjectRepository.deleteById(id);
    }

    public void update(Integer id, Subject subjectUpdated) throws SubjectNotFoundException {
        this.getById(id);
        this.subjectRepository.save(subjectUpdated);
    }

    public List<Subject> getCorrelativesById(Integer id) {
        return subjectRepository.getCorrelativesById(id);
    }
}
