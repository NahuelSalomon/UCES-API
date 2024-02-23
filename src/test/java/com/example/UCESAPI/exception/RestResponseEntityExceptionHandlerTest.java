package com.example.UCESAPI.exception;

import com.example.UCESAPI.exception.notfound.ObjectNotFoundException;
import com.example.UCESAPI.utils.EntityResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

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
        when(ex.getConstraintViolations()).thenReturn(violations);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        ResponseEntity<Object> responseEntity = exceptionHandler.handleConstraintViolation(ex, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void handlerObjectNotFoundException() {
        ObjectNotFoundException ex = new ObjectNotFoundException();
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> responseEntity = exceptionHandler.handlerObjectNotFoundException(ex, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(EntityResponse.messageResponse(null));
    }

    @Test
    void handlerExpiredJwtException() {
        ExpiredJwtException ex = new ExpiredJwtException(null, null, "Token expired");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> responseEntity = exceptionHandler.handlerObjectNotFoundException(ex, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(responseEntity.getBody()).isEqualTo(EntityResponse.messageResponse("Token expired"));
    }

    @Test
    void handlerAccessNotAllowedException() {
        AccessNotAllowedException ex = new AccessNotAllowedException("Access not allowed");
        WebRequest request = mock(WebRequest.class);

        ResponseEntity<Object> responseEntity = exceptionHandler.handlerAccessNotAllowedException(ex, request);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        assertThat(responseEntity.getBody()).isEqualTo(EntityResponse.messageResponse("Access not allowed"));
    }


}
