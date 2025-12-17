package com.moneygram.tecnical.test.service;

import com.moneygram.tecnical.test.dto.BookRequest;
import com.moneygram.tecnical.test.mapper.BookMapper;
import com.moneygram.tecnical.test.model.Book;
import com.moneygram.tecnical.test.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    
    private final BookRepository repository;
    private final BookMapper bookMapper;

    public BookService(BookRepository repository, BookMapper bookMapper) {
        this.repository = repository;
        this.bookMapper = bookMapper;
    }

    public Book create(BookRequest bookRequest) {
        Book book = bookMapper.toBook(bookRequest);
        return repository.save(book);
    }

    public Optional<Book> findById(Long id) {
        return repository.findById(id);
    }

    public List<Book> findAll() {
        return repository.findAll();
    }

    public Optional<Book> update(Long id, BookRequest bookRequest) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        Book book = bookMapper.toBook(id, bookRequest);
        return Optional.of(repository.save(book));
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}