package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.CareerNotFoundException;
import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.model.CareerStatistics;
import com.example.UCESAPI.repository.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CareerService {

    private final CareerRepository careerRepository;
    private final CareerStatisticsService careerStatisticsService;

    @Autowired
    public CareerService(CareerRepository careerRepository, CareerStatisticsService careerStatisticsService) {
        this.careerRepository = careerRepository;
        this.careerStatisticsService = careerStatisticsService;
    }

    public Career add(Career career) {
        CareerStatistics newStatistic = careerStatisticsService.add(new CareerStatistics(null, 0f,0));
        career.setStatistics(newStatistic);
        return this.careerRepository.save(career);
    }

    public Page<Career> getAll(Pageable pageable) {
        return this.careerRepository.findAll(pageable);
    }

    public Career getById(Integer id) throws CareerNotFoundException {
        return this.careerRepository.findById(id).orElseThrow(CareerNotFoundException::new);
    }

    public void deleteById(Integer id) {
        this.careerRepository.deleteById(id);
    }

    public void update(Integer id, Career careerUpdated) throws CareerNotFoundException {
        this.getById(id);
        this.careerRepository.save(careerUpdated);
    }

}
