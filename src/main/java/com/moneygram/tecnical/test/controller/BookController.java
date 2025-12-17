package com.moneygram.tecnical.test.controller;

import com.moneygram.tecnical.test.dto.BookRequest;
import com.moneygram.tecnical.test.model.Book;
import com.moneygram.tecnical.test.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);
    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Book> create(@Valid @RequestBody BookRequest request) {
        log.info("Creating book: {}", request.title());
        Book book = service.create(request);
        log.info("Book created with id: {}", book.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAll() {
        log.info("Fetching all books");
        List<Book> books = service.findAll();
        log.info("Found {} books", books.size());
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        log.info("Fetching book with id: {}", id);
        return service.findById(id)
                .map(book -> {
                    log.info("Book found: {}", book.title());
                    return ResponseEntity.ok(book);
                })
                .orElseGet(() -> {
                    log.warn("Book not found with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        log.info("Updating book with id: {}", id);
        return service.update(id, request)
                .map(book -> {
                    log.info("Book updated: {}", book.title());
                    return ResponseEntity.ok(book);
                })
                .orElseGet(() -> {
                    log.warn("Book not found for update with id: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Deleting book with id: {}", id);
        boolean deleted = service.delete(id);
        if (deleted) {
            log.info("Book deleted with id: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Book not found for deletion with id: {}", id);
            return ResponseEntity.notFound().build();
        }
    }


}