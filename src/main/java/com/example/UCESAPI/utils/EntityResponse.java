package com.example.UCESAPI.utils;

import com.example.UCESAPI.model.dto.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class  EntityResponse {

    public static <T> ResponseEntity<List<T>> pageResponse(Page<T> page) {
        if (!page.getContent().isEmpty()) {
            return ResponseEntity.
                    status(HttpStatus.OK).
                    header("X-Total-Count", Long.toString(page.getTotalElements())).
                    header("X-Total-Pages", Long.toString(page.getTotalPages())).
                    body(page.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(page.getContent());
        }
    }

    public static <T> ResponseEntity<List<T>> listResponse(List<T> list) {
        if (!list.isEmpty()) {
            return ResponseEntity.
                    status(HttpStatus.OK).
                    body(list);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(list);
        }
    }

    public static Response messageResponse(String message) {
        return Response.builder().message(message).build();
    }

}
