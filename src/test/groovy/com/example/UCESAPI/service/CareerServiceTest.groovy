package com.example.UCESAPI.service

import com.example.UCESAPI.exception.notfound.CareerNotFoundException
import com.example.UCESAPI.model.Career
import com.example.UCESAPI.model.CareerStatistics
import com.example.UCESAPI.repository.CareerRepository
import com.example.UCESAPI.repository.CareerStatisticsRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class CareerServiceTest extends Specification{

    CareerRepository careerRepository
    CareerStatisticsService careerStatisticsService
    CareerStatisticsRepository careerStatisticsRepository
    CareerService sut

    def setup(){
        this.careerRepository = Mock(CareerRepository)
        this.careerStatisticsRepository = Mock(CareerStatisticsRepository)
        this.careerStatisticsService = new CareerStatisticsService(this.careerStatisticsRepository)
        sut = new CareerService(this.careerRepository, this.careerStatisticsService)
    }

    def 'getById happy path'(){
        when:
        Career result = sut.getById(1)
        then:
        1 * careerRepository.findById(1) >> Optional.of(new Career(id: 1))
        assert result.id == 1
    }

    def 'getById throws an exception when the career is not found'(){
        when:
        sut.getById(1)
        then:
        1 * careerRepository.findById(1) >> Optional.empty()
        thrown(CareerNotFoundException)
    }

    def 'add career happy path'(){
        setup:
        CareerStatistics mockStatistics = new CareerStatistics(id: 1)
        when:
        Career result = sut.add(new Career(name: 'Math'))
        then:
        1 * careerStatisticsRepository.save(_) >> mockStatistics
        1 * careerRepository.save(_) >> new Career(id: 1, statistics: mockStatistics, name: 'Math')
        assert result.statistics.id == 1
    }

    def 'getAll happy path'(){
        setup:
        Pageable mockPageable = Pageable.ofSize(2)
        when:
        Page<Career> result = sut.getAll(mockPageable)
        then:
        1 * careerRepository.findAll(mockPageable) >> mockedCareerPage
        assert result.totalElements == 2L
    }

    def 'deleteById happy path'(){
        when:
        sut.deleteById(1)
        then:
        1 * careerRepository.deleteById(1)
    }

    def 'update happy path'(){
        setup:
        Career updated = new Career(name: 'Math 2', statistics: new CareerStatistics(id: 1))
        when:
        sut.update(1, updated)
        then:
        1 * careerRepository.findById(1) >> Optional.of(mockedCareer)
        1 * careerRepository.save(updated) >> new Career(id: 1, name: 'Math 2', statistics: new CareerStatistics(id: 1))
    }

    protected static Career getMockedCareer(){
        new Career(id: 1, name: 'Math', statistics: new CareerStatistics(id: 1))
    }

    protected static Page<Career> getMockedCareerPage(){
        new PageImpl<>(List.of(
                mockedCareer,
                new Career(id: 2, name: 'Science', statistics: new CareerStatistics(id: 2))
        ))
    }
}
