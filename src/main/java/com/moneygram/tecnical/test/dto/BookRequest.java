package com.moneygram.tecnical.test.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BookRequest(
    @NotBlank String title,
    @NotBlank String author,
    @NotBlank String isbn,
    @NotNull @Positive Integer publishedYear,
    @NotBlank String genre
) {}