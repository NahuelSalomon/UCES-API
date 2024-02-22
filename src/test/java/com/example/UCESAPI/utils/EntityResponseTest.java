package com.example.UCESAPI.utils;

import com.example.UCESAPI.model.dto.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityResponseTest {
    @Test
    public void testPageResponse_NotEmptyPage() {
        // Arrange
        Page<String> page = new PageImpl<>(Collections.singletonList(""));

        // Act
        ResponseEntity<List<String>> responseEntity = EntityResponse.pageResponse(page);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(page.getContent(), responseEntity.getBody());
        assertEquals("1", responseEntity.getHeaders().getFirst("X-Total-Count"));
        assertEquals("1", responseEntity.getHeaders().getFirst("X-Total-Pages"));
    }

    @Test
    public void testPageResponse_EmptyPage() {
        // Arrange
        Page<String> page = new PageImpl<>(Collections.emptyList());

        // Act
        ResponseEntity<List<String>> responseEntity = EntityResponse.pageResponse(page);

        //Assert
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());
    }

    @Test
    public void testListResponse_NotEmptyList() {
        // Arrange
        List<String> list = Collections.singletonList("Test");

        // Act
        ResponseEntity<List<String>> responseEntity = EntityResponse.listResponse(list);

        //Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(list, responseEntity.getBody());
    }

    @Test
    public void testListResponse_EmptyList() {
        // Crear una lista vacía
        List<String> list = Collections.emptyList();

        // Ejecutar el método a probar
        ResponseEntity<List<String>> responseEntity = EntityResponse.listResponse(list);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertEquals(Collections.emptyList(), responseEntity.getBody());
    }

    @Test
    public void testMessageResponse() {
        String message = "Test message";

        Response response = EntityResponse.messageResponse(message);

        assertEquals(message, response.getMessage());
    }

}
