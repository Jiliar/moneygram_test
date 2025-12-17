package com.moneygram.tecnical.test.service;

import com.moneygram.tecnical.test.dto.BookRequest;
import com.moneygram.tecnical.test.mapper.BookMapper;
import com.moneygram.tecnical.test.model.Book;
import com.moneygram.tecnical.test.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    
    private static final Logger log = LoggerFactory.getLogger(BookService.class);
    private final BookRepository repository;
    private final BookMapper bookMapper;

    public BookService(BookRepository repository, BookMapper bookMapper) {
        this.repository = repository;
        this.bookMapper = bookMapper;
    }

    public Book create(BookRequest bookRequest) {
        log.debug("Creating book from request: {}", bookRequest.title());
        Book book = bookMapper.toBook(bookRequest);
        Book saved = repository.save(book);
        log.debug("Book saved with id: {}", saved.id());
        return saved;
    }

    public Optional<Book> findById(Long id) {
        log.debug("Finding book by id: {}", id);
        return repository.findById(id);
    }

    public List<Book> findAll() {
        log.debug("Finding all books");
        return repository.findAll();
    }

    public Optional<Book> update(Long id, BookRequest bookRequest) {
        log.debug("Updating book with id: {}", id);
        if (!repository.existsById(id)) {
            log.debug("Book not found for update: {}", id);
            return Optional.empty();
        }
        Book book = bookMapper.toBook(id, bookRequest);
        Book updated = repository.save(book);
        log.debug("Book updated: {}", updated.id());
        return Optional.of(updated);
    }

    public boolean delete(Long id) {
        log.debug("Deleting book with id: {}", id);
        if (!repository.existsById(id)) {
            log.debug("Book not found for deletion: {}", id);
            return false;
        }
        repository.deleteById(id);
        log.debug("Book deleted: {}", id);
        return true;
    }
}