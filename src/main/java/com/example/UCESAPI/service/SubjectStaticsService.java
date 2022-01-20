package com.example.UCESAPI.service;
import com.example.UCESAPI.exception.SubjectStaticsNotExistsException;
import com.example.UCESAPI.model.SubjectStatics;
import com.example.UCESAPI.repository.SubjectStaticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubjectStaticsService {

    private final SubjectStaticsRepository subjectStaticsRepository;

    @Autowired
    public SubjectStaticsService(SubjectStaticsRepository subjectStaticsRepository) {
        this.subjectStaticsRepository = subjectStaticsRepository;
    }

    public SubjectStatics add(SubjectStatics subjectStatics) {
        return this.subjectStaticsRepository.save(subjectStatics);
    }

    public Page<SubjectStatics> getAll(Pageable pageable) {
        return this.subjectStaticsRepository.findAll(pageable);
    }

    public SubjectStatics getById(Integer id) throws SubjectStaticsNotExistsException {
        return this.subjectStaticsRepository.findById(id).orElseThrow(SubjectStaticsNotExistsException::new);
    }

    public void deleteById(Integer id) {
        this.subjectStaticsRepository.deleteById(id);
    }

    public void update(Integer id, SubjectStatics subjectStaticsUpdated) throws SubjectStaticsNotExistsException {
        this.getById(id);
        this.subjectStaticsRepository.save(subjectStaticsUpdated);
    }

}
