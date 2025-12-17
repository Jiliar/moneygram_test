package com.moneygram.tecnical.test.repository;

import com.moneygram.tecnical.test.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {

    private BookRepository repository;

    @BeforeEach
    void setUp() {
        repository = new BookRepository();
    }

    @Test
    void save_ShouldGenerateIdAndSaveBook() {
        Book book = new Book(null, "Title", "Author", "ISBN", 2023, "Genre");
        
        Book saved = repository.save(book);
        
        assertNotNull(saved.id());
        assertEquals("Title", saved.title());
    }

    @Test
    void findById_ShouldReturnBook_WhenExists() {
        Book book = repository.save(new Book(null, "Title", "Author", "ISBN", 2023, "Genre"));
        
        Optional<Book> found = repository.findById(book.id());
        
        assertTrue(found.isPresent());
        assertEquals(book.title(), found.get().title());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenNotExists() {
        Optional<Book> found = repository.findById(999L);
        
        assertFalse(found.isPresent());
    }

    @Test
    void findAll_ShouldReturnAllBooks() {
        repository.save(new Book(null, "Title1", "Author1", "ISBN1", 2023, "Genre1"));
        repository.save(new Book(null, "Title2", "Author2", "ISBN2", 2024, "Genre2"));
        
        List<Book> books = repository.findAll();
        
        assertEquals(2, books.size());
    }

    @Test
    void deleteById_ShouldRemoveBook() {
        Book book = repository.save(new Book(null, "Title", "Author", "ISBN", 2023, "Genre"));
        
        repository.deleteById(book.id());
        
        assertFalse(repository.existsById(book.id()));
    }

    @Test
    void existsById_ShouldReturnTrue_WhenExists() {
        Book book = repository.save(new Book(null, "Title", "Author", "ISBN", 2023, "Genre"));
        
        assertTrue(repository.existsById(book.id()));
    }

    @Test
    void existsById_ShouldReturnFalse_WhenNotExists() {
        assertFalse(repository.existsById(999L));
    }
}
