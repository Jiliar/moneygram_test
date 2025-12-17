package com.moneygram.tecnical.test.model;

public record Book(
    Long id,
    String title,
    String author,
    String isbn,
    Integer publishedYear,
    String genre
) {}