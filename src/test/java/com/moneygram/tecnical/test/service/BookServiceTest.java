package com.moneygram.tecnical.test.service;

import com.moneygram.tecnical.test.dto.BookRequest;
import com.moneygram.tecnical.test.mapper.BookMapper;
import com.moneygram.tecnical.test.model.Book;
import com.moneygram.tecnical.test.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository repository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService service;

    @Test
    void create_ShouldReturnSavedBook() {
        BookRequest request = new BookRequest("Title", "Author", "ISBN", 2023, "Genre");
        Book book = new Book(null, "Title", "Author", "ISBN", 2023, "Genre");
        Book savedBook = new Book(1L, "Title", "Author", "ISBN", 2023, "Genre");

        when(bookMapper.toBook(request)).thenReturn(book);
        when(repository.save(book)).thenReturn(savedBook);

        Book result = service.create(request);

        assertEquals(1L, result.id());
        verify(repository).save(book);
    }

    @Test
    void findById_ShouldReturnBook_WhenExists() {
        Book book = new Book(1L, "Title", "Author", "ISBN", 2023, "Genre");
        when(repository.findById(1L)).thenReturn(Optional.of(book));

        Optional<Book> result = service.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().id());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<Book> result = service.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllBooks() {
        List<Book> books = List.of(
            new Book(1L, "Title1", "Author1", "ISBN1", 2023, "Genre1"),
            new Book(2L, "Title2", "Author2", "ISBN2", 2024, "Genre2")
        );
        when(repository.findAll()).thenReturn(books);

        List<Book> result = service.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void update_ShouldReturnUpdatedBook_WhenExists() {
        BookRequest request = new BookRequest("Updated", "Author", "ISBN", 2023, "Genre");
        Book updatedBook = new Book(1L, "Updated", "Author", "ISBN", 2023, "Genre");

        when(repository.existsById(1L)).thenReturn(true);
        when(bookMapper.toBook(1L, request)).thenReturn(updatedBook);
        when(repository.save(updatedBook)).thenReturn(updatedBook);

        Optional<Book> result = service.update(1L, request);

        assertTrue(result.isPresent());
        assertEquals("Updated", result.get().title());
    }

    @Test
    void update_ShouldReturnEmpty_WhenNotExists() {
        BookRequest request = new BookRequest("Updated", "Author", "ISBN", 2023, "Genre");
        when(repository.existsById(1L)).thenReturn(false);

        Optional<Book> result = service.update(1L, request);

        assertFalse(result.isPresent());
        verify(repository, never()).save(any());
    }

    @Test
    void delete_ShouldReturnTrue_WhenExists() {
        when(repository.existsById(1L)).thenReturn(true);

        boolean result = service.delete(1L);

        assertTrue(result);
        verify(repository).deleteById(1L);
    }

    @Test
    void delete_ShouldReturnFalse_WhenNotExists() {
        when(repository.existsById(1L)).thenReturn(false);

        boolean result = service.delete(1L);

        assertFalse(result);
        verify(repository, never()).deleteById(any());
    }
}
