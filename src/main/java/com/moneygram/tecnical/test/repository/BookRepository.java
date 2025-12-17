package com.moneygram.tecnical.test.repository;

import com.moneygram.tecnical.test.model.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {
    
    private static final Logger log = LoggerFactory.getLogger(BookRepository.class);
    private final Map<Long, Book> books = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Book save(Book book) {
        Long id = book.id() != null ? book.id() : idGenerator.getAndIncrement();
        Book savedBook = new Book(id, book.title(), book.author(), book.isbn(), book.publishedYear(), book.genre());
        books.put(id, savedBook);
        log.debug("Book saved in repository with id: {}", id);
        return savedBook;
    }

    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(books.get(id));
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values());
    }

    public void deleteById(Long id) {
        books.remove(id);
        log.debug("Book removed from repository with id: {}", id);
    }

    public boolean existsById(Long id) {
        return books.containsKey(id);
    }
}