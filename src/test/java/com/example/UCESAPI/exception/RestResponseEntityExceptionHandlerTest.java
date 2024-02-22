package com.example.UCESAPI.exception;

import static org.mockito.Mockito.mock;

import com.example.UCESAPI.exception.notfound.ObjectNotFoundException;
import com.example.UCESAPI.utils.EntityResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class RestResponseEntityExceptionHandlerTest {

    @InjectMocks
    private RestResponseEntityExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleConstraintViolation() {
        ConstraintViolationException ex = mock(ConstraintViolationException.class);
        WebRequest request = mock(WebRequest.class);

        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
        ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);
        violations.add(violation1);
        violations.add(violation2);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        ResponseEntity<Object> responseEntity = exceptionHandler.handleConstraintViolation(ex, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        //assertThat(responseEntity.getHeaders()).isEqualTo(headers);
    }

    @Test
    void handlerObjectNotFoundException() {
        ObjectNotFoundException ex = new ObjectNotFoundException();
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> responseEntity = exceptionHandler.handlerObjectNotFoundException(ex, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(EntityResponse.messageResponse(null));
    }

//    @Test
//    void handlerExpiredJwtException() {
//        ExpiredJwtException ex = new ExpiredJwtException(null, null, "Token expired");
//        WebRequest request = mock(WebRequest.class);
//
//        ResponseEntity<Object> responseEntity = exceptionHandler.handlerExpiredJwtException(ex, request);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//        assertThat(responseEntity.getBody()).isEqualTo(EntityResponse.messageResponse("Token expired"));
//    }


}
