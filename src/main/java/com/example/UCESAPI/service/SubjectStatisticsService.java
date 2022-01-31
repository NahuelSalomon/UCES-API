package com.example.UCESAPI.service;
import com.example.UCESAPI.exception.SubjectStaticsNotExistsException;
import com.example.UCESAPI.model.SubjectStatistics;
import com.example.UCESAPI.repository.SubjectStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SubjectStatisticsService {

    private final SubjectStatisticsRepository subjectStatisticsRepository;

    @Autowired
    public SubjectStatisticsService(SubjectStatisticsRepository subjectStatisticsRepository) {
        this.subjectStatisticsRepository = subjectStatisticsRepository;
    }

    public SubjectStatistics add(SubjectStatistics subjectStatistics) {
        return this.subjectStatisticsRepository.save(subjectStatistics);
    }

    public Page<SubjectStatistics> getAll(Pageable pageable) {
        return this.subjectStatisticsRepository.findAll(pageable);
    }

    public SubjectStatistics getById(Integer id) throws SubjectStaticsNotExistsException {
        return this.subjectStatisticsRepository.findById(id).orElseThrow(SubjectStaticsNotExistsException::new);
    }

    public void deleteById(Integer id) {
        this.subjectStatisticsRepository.deleteById(id);
    }

    public void update(Integer id, SubjectStatistics subjectStatisticsUpdated) throws SubjectStaticsNotExistsException {
        this.getById(id);
        this.subjectStatisticsRepository.save(subjectStatisticsUpdated);
    }

}
