package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.CareerNotFoundException;
import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.repository.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CareerService {

    private final CareerRepository careerRepository;


    @Autowired
    public CareerService(CareerRepository careerRepository) {
        this.careerRepository = careerRepository;
    }

    public Career add(Career career) {
        return this.careerRepository.save(career);
    }

    public Page<Career> getAll(Pageable pageable) {
        return this.careerRepository.findAll(pageable);
    }

    public Career getById(Integer id) throws CareerNotFoundException {
        return this.careerRepository.findById(id).orElseThrow(CareerNotFoundException::new);
    }

    public Career getByName(String name) throws CareerNotFoundException {
        return this.careerRepository.findByNameIgnoreCase(name).orElseThrow(CareerNotFoundException::new);
    }

    public void deleteById(Integer id) {
        this.careerRepository.deleteById(id);
    }

    public void update(Integer id, Career careerUpdated) throws CareerNotFoundException {
        this.getById(id);
        this.careerRepository.save(careerUpdated);
    }

}
