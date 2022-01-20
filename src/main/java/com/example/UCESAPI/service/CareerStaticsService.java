package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.CareerNotExistsException;
import com.example.UCESAPI.exception.CareerStatisticsNotExistsException;
import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.model.CareerStatistics;
import com.example.UCESAPI.repository.CareerRepository;
import com.example.UCESAPI.repository.CareerStaticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CareerStaticsService {

    private final CareerStaticsRepository careerStaticsRepository;

    @Autowired
    public CareerStaticsService(CareerStaticsRepository careerStaticsRepository) {
        this.careerStaticsRepository = careerStaticsRepository;
    }

    public CareerStatistics add(CareerStatistics careerStatistics) {
        return this.careerStaticsRepository.save(careerStatistics);
    }

    public Page<CareerStatistics> getAll(Pageable pageable) {
        return this.careerStaticsRepository.findAll(pageable);
    }

    public CareerStatistics getById(Integer id) throws CareerStatisticsNotExistsException {
        return this.careerStaticsRepository.findById(id).orElseThrow(CareerStatisticsNotExistsException::new);
    }

    public void deleteById(Integer id) {
        this.careerStaticsRepository.deleteById(id);
    }

    public void update(Integer id, CareerStatistics careerStatisticsUpdated) throws CareerStatisticsNotExistsException {
        this.getById(id);
        this.careerStaticsRepository.save(careerStatisticsUpdated);
    }

}
