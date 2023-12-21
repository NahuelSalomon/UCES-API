package com.example.UCESAPI.controller;

import com.example.UCESAPI.controller.CareerController;
import com.example.UCESAPI.exception.notfound.CareerNotFoundException;
import com.example.UCESAPI.model.Career;
import com.example.UCESAPI.service.CareerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CareerControllerTest {

    @Mock
    private CareerService careerService;

    @InjectMocks
    private CareerController careerController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCareer() {
        // Arrange
        Career careerToAdd = new Career(1,"Ingeniería en sistemas","Descripción",5);
        when(careerService.add(any(Career.class))).thenReturn(careerToAdd);

        // Act
        ResponseEntity<Object> response = careerController.add(careerToAdd);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(careerToAdd, response.getBody());
    }

    @Test
    void testGetAllCareers() {
        // Arrange
        List<Career> careerList = Collections.singletonList(new Career(1,"Ingeniería en sistemas","Descripción",5));
        Page<Career> careerPage = new PageImpl<>(careerList);
        when(careerService.getAll(any())).thenReturn(careerPage);

        // Act
        ResponseEntity<List<Career>> response = careerController.getAll(10, 0);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(careerList, response.getBody());
    }

    @Test
    void testGetByName() throws CareerNotFoundException {
        // Arrange
        String careerName = "ExampleCareer";
        Career career = new Career();
        when(careerService.getByName(eq(careerName))).thenReturn(career);

        // Act
        ResponseEntity<Career> response = careerController.getByName(careerName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(career, response.getBody());
    }

    @Test
    void testGetById() throws CareerNotFoundException {
        // Arrange
        Integer careerId = 1;
        Career career = new Career();
        when(careerService.getById(eq(careerId))).thenReturn(career);

        // Act
        ResponseEntity<Career> response = careerController.getById(careerId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(career, response.getBody());
    }

    @Test
    void testDeleteById() {
        // Arrange
        Integer careerId = 1;

        // Act
        ResponseEntity<Object> response = careerController.deleteById(careerId);

        // Assert
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(careerService, times(1)).deleteById(eq(careerId));
    }


}
