package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.CareerNotFoundException;
import com.example.UCESAPI.exception.notfound.SubjectNotFoundException;
import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.model.Subject;
import com.example.UCESAPI.repository.SubjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.UCESAPI.utils.ModelTestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubjectServiceTest {

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private CareerService careerService;

    @InjectMocks
    private SubjectService subjectService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testAdd() {
        // Arrange
        Subject subject = someSubject();
        when(subjectRepository.save(subject)).thenReturn(subject);

        // Act
        Subject addedSubject = subjectService.add(subject);

        // Assert
        assertNotNull(addedSubject);
        assertSame(subject, addedSubject);
        verify(subjectRepository, times(1)).save(subject);
    }

    @Test
    void testGetAll() {
        // Arrange
        Pageable pageable = somePageable();
        Page<Subject> subjectPage = someSubjectPage();
        when(subjectRepository.findAll(pageable)).thenReturn(subjectPage);

        // Act
        Page<Subject> result = subjectService.getAll(pageable);

        // Assert
        assertNotNull(result);
        assertSame(subjectPage, result);
        verify(subjectRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetByIdWhenSubjectExists() throws SubjectNotFoundException {
        // Arrange
        int subjectId = 1;
        Subject subject = someSubject();
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.of(subject));

        // Act
        Subject result = subjectService.getById(subjectId);

        // Assert
        assertNotNull(result);
        assertSame(subject, result);
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void testGetByIdWhenSubjectDoesNotExist() {
        // Arrange
        int subjectId = 1;
        when(subjectRepository.findById(subjectId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SubjectNotFoundException.class, () -> subjectService.getById(subjectId));
        verify(subjectRepository, times(1)).findById(subjectId);
    }

    @Test
    void testGetByCareerWhenCareerExists() throws CareerNotFoundException {
        // Arrange
        int careerId = 1;
        Career career = someCareer();
        when(careerService.getById(careerId)).thenReturn(career);
        when(subjectRepository.findAllByCareer(career)).thenReturn(Collections.emptyList());

        // Act
        List<Subject> result = subjectService.getByCareer(careerId);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(careerService, times(1)).getById(careerId);
        verify(subjectRepository, times(1)).findAllByCareer(career);
    }

    @Test
    void testGetByCareerWhenCareerDoesNotExist() throws CareerNotFoundException {
        // Arrange
        int careerId = 1;
        when(careerService.getById(careerId)).thenThrow(new CareerNotFoundException());

        // Act & Assert
        assertThrows(CareerNotFoundException.class, () -> subjectService.getByCareer(careerId));
        verify(careerService, times(1)).getById(careerId);
        verifyNoInteractions(subjectRepository);
    }

    @Test
    void testGetByNameAndCareerIdWhenSubjectExists() throws SubjectNotFoundException {
        // Arrange
        int careerId = 1;
        String subjectName = "Math 1";
        Subject subject = someSubject();
        when(subjectRepository.findByNameAndCareerIdIgnoreCase(subjectName, careerId)).thenReturn(Optional.of(subject));

        // Act
        Subject result = subjectService.getByNameAndCareerId(subjectName, careerId);

        // Assert
        assertNotNull(result);
        assertSame(subject, result);
        verify(subjectRepository, times(1)).findByNameAndCareerIdIgnoreCase(subjectName, careerId);
    }

    @Test
    void testGetByNameAndCareerIdWhenSubjectDoesNotExist() {
        // Arrange
        String subjectName = "Math";
        int careerId = 1;
        when(subjectRepository.findByNameAndCareerIdIgnoreCase(subjectName, careerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SubjectNotFoundException.class, () -> subjectService.getByNameAndCareerId(subjectName, careerId));
        verify(subjectRepository, times(1)).findByNameAndCareerIdIgnoreCase(subjectName, careerId);
    }

    @Test
    void testDeleteById() {
        // Arrange
        int subjectId = 1;

        // Act
        subjectService.deleteById(subjectId);

        // Assert
        verify(subjectRepository, times(1)).deleteById(subjectId);
    }


}
