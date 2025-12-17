package com.moneygram.tecnical.test.controller;

import com.moneygram.tecnical.test.dto.BookRequest;
import com.moneygram.tecnical.test.model.Book;
import com.moneygram.tecnical.test.service.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookService service;

    @InjectMocks
    private BookController controller;

    @Test
    void create_ShouldReturn201_WhenValidRequest() {
        BookRequest request = new BookRequest("Title", "Author", "ISBN", 2023, "Genre");
        Book book = new Book(1L, "Title", "Author", "ISBN", 2023, "Genre");

        when(service.create(any(BookRequest.class))).thenReturn(book);

        ResponseEntity<Book> response = controller.create(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1L, response.getBody().id());
        assertEquals("Title", response.getBody().title());
    }

    @Test
    void findAll_ShouldReturn200_WithBookList() {
        List<Book> books = List.of(
            new Book(1L, "Title1", "Author1", "ISBN1", 2023, "Genre1"),
            new Book(2L, "Title2", "Author2", "ISBN2", 2024, "Genre2")
        );

        when(service.findAll()).thenReturn(books);

        ResponseEntity<List<Book>> response = controller.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void findById_ShouldReturn200_WhenBookExists() {
        Book book = new Book(1L, "Title", "Author", "ISBN", 2023, "Genre");

        when(service.findById(1L)).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = controller.findById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().id());
    }

    @Test
    void findById_ShouldReturn404_WhenBookNotExists() {
        when(service.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Book> response = controller.findById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void update_ShouldReturn200_WhenBookExists() {
        BookRequest request = new BookRequest("Updated", "Author", "ISBN", 2023, "Genre");
        Book book = new Book(1L, "Updated", "Author", "ISBN", 2023, "Genre");

        when(service.update(eq(1L), any(BookRequest.class))).thenReturn(Optional.of(book));

        ResponseEntity<Book> response = controller.update(1L, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated", response.getBody().title());
    }

    @Test
    void update_ShouldReturn404_WhenBookNotExists() {
        BookRequest request = new BookRequest("Updated", "Author", "ISBN", 2023, "Genre");

        when(service.update(eq(1L), any(BookRequest.class))).thenReturn(Optional.empty());

        ResponseEntity<Book> response = controller.update(1L, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void delete_ShouldReturn204_WhenBookExists() {
        when(service.delete(1L)).thenReturn(true);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void delete_ShouldReturn404_WhenBookNotExists() {
        when(service.delete(1L)).thenReturn(false);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
