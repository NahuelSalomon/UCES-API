package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.CareerStatisticsNotExistsException;
import com.example.UCESAPI.model.CareerStatistics;
import com.example.UCESAPI.repository.CareerStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CareerStatisticsService {

    private final CareerStatisticsRepository careerStatisticsRepository;

    @Autowired
    public CareerStatisticsService(CareerStatisticsRepository careerStatisticsRepository) {
        this.careerStatisticsRepository = careerStatisticsRepository;
    }

    public CareerStatistics add(CareerStatistics careerStatistics) {
        return this.careerStatisticsRepository.save(careerStatistics);
    }

    public Page<CareerStatistics> getAll(Pageable pageable) {
        return this.careerStatisticsRepository.findAll(pageable);
    }

    public CareerStatistics getById(Integer id) throws CareerStatisticsNotExistsException {
        return this.careerStatisticsRepository.findById(id).orElseThrow(CareerStatisticsNotExistsException::new);
    }

    public void deleteById(Integer id) {
        this.careerStatisticsRepository.deleteById(id);
    }

    public void update(Integer id, CareerStatistics careerStatisticsUpdated) throws CareerStatisticsNotExistsException {
        this.getById(id);
        this.careerStatisticsRepository.save(careerStatisticsUpdated);
    }

}
