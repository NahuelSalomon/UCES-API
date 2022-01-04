package com.example.UCESAPI.repository;

import com.example.UCESAPI.model.CareerStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerStaticsRepository extends JpaRepository<CareerStatistics,Integer> {

}
