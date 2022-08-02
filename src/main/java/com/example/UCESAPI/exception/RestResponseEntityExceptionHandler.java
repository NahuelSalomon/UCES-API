package com.example.UCESAPI.exception;

import com.example.UCESAPI.exception.notfound.ObjectNotFoundException;
import com.example.UCESAPI.model.response.ApiError;
import com.example.UCESAPI.utils.EntityResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        List<String> errors = new ArrayList<String>();

        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass()+" "+violation.getMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);

        return new ResponseEntity<Object>(apiError, new HttpHeaders(),apiError.getHttpStatus());
    }

    @ExceptionHandler({ObjectNotFoundException.class})
    public ResponseEntity<Object> handlerObjectNotFoundException(ObjectNotFoundException ex,WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(EntityResponse.messageResponse(ex.getMessage()));
    }

    @ExceptionHandler({ExpiredJwtException.class})
    public ResponseEntity<Object> handlerObjectNotFoundException(ExpiredJwtException ex,WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(EntityResponse.messageResponse(ex.getMessage()));
    }

    @ExceptionHandler({AccessNotAllowedException.class})
    public ResponseEntity<Object> handlerAccessNotAllowedException(AccessNotAllowedException ex,WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(EntityResponse.messageResponse(ex.getMessage()));
    }

}
