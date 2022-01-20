package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.CareerNotExistsException;
import com.example.UCESAPI.exception.ProfessorNotExistsException;
import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.model.Professor;
import com.example.UCESAPI.repository.CareerRepository;
import com.example.UCESAPI.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    @Autowired
    public ProfessorService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public Professor add(Professor professor) {
        return this.professorRepository.save(professor);
    }

    public Page<Professor> getAll(Pageable pageable) {
        return this.professorRepository.findAll(pageable);
    }

    public Professor getById(Integer id) throws ProfessorNotExistsException {
        return this.professorRepository.findById(id).orElseThrow(ProfessorNotExistsException::new);
    }

    public void deleteById(Integer id) {
        this.professorRepository.deleteById(id);
    }

    public void update(Integer id, Professor professorUpdated) throws ProfessorNotExistsException {
        this.getById(id);
        this.professorRepository.save(professorUpdated);
    }

}
