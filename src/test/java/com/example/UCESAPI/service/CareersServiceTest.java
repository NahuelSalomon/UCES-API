package com.example.UCESAPI.service;

import com.example.UCESAPI.exception.notfound.CareerNotFoundException;
import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.repository.CareerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class CareersServiceTest {

    @Mock
    private CareerRepository careerRepository;

    @InjectMocks
    private CareerService careerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCareer() {
        // Arrange
        Career careerToAdd = new Career(/* create a Career object with necessary data */);
        when(careerRepository.save(any())).thenReturn(careerToAdd);

        // Act
        Career addedCareer = careerService.add(careerToAdd);

        // Assert
        assertNotNull(addedCareer);
        // Add more assertions based on your specific implementation and requirements
    }

    @Test
    void testGetAllCareers() {
        // Arrange
        Page<Career> careerPage = new PageImpl<>(Collections.singletonList(new Career()));
        when(careerRepository.findAll(any(Pageable.class))).thenReturn(careerPage);

        // Act
        Page<Career> resultPage = careerService.getAll(Pageable.unpaged());

        // Assert
        assertNotNull(resultPage);
    }

    @Test
    void testGetCareerById() throws CareerNotFoundException {
        // Arrange
        Integer careerId = 1;
        Career career = new Career(/* create a Career object with necessary data */);
        when(careerRepository.findById(eq(careerId))).thenReturn(java.util.Optional.of(career));

        // Act
        Career resultCareer = careerService.getById(careerId);

        // Assert
        assertNotNull(resultCareer);
    }

    @Test
    void testGetCareerByIdThrowsCareerNotFoundException() {
        // Arrange
        Integer careerId = 1;
        when(careerRepository.findById(eq(careerId))).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(CareerNotFoundException.class, () -> careerService.getById(careerId));
    }

    @Test
    void testGetCareerByName() throws CareerNotFoundException {
        // Arrange
        String careerName = "ExampleCareer";
        Career career = new Career();
        when(careerRepository.findByNameIgnoreCase(eq(careerName))).thenReturn(java.util.Optional.of(career));

        // Act
        Career resultCareer = careerService.getByName(careerName);

        // Assert
        assertNotNull(resultCareer);
    }

    @Test
    void testGetCareerByNameThrowsCareerNotFoundException() {
        // Arrange
        String careerName = "NonExistentCareer";
        when(careerRepository.findByNameIgnoreCase(eq(careerName))).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(CareerNotFoundException.class, () -> careerService.getByName(careerName));
    }

    @Test
    void testDeleteCareerById() {
        // Arrange
        Integer careerId = 1;
        when(careerRepository.existsById(eq(careerId))).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> careerService.deleteById(careerId));
    }

    @Test
    void testUpdateCareer() {
        // Arrange
        Integer careerId = 1;
        Career existingCareer = new Career();
        Career updatedCareer = new Career();
        when(careerRepository.findById(eq(careerId))).thenReturn(java.util.Optional.of(existingCareer));

        // Act
        assertDoesNotThrow(() -> careerService.update(careerId, updatedCareer));
    }

    @Test
    void testUpdateCareerThrowsCareerNotFoundException() {
        // Arrange
        Integer careerId = 1;
        Career updatedCareer = new Career(/* create an updated Career object with necessary data */);
        when(careerRepository.findById(eq(careerId))).thenReturn(java.util.Optional.empty());

        // Act & Assert
        assertThrows(CareerNotFoundException.class, () -> careerService.update(careerId, updatedCareer));
    }

}
