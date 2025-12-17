package com.moneygram.tecnical.test.mapper;

import com.moneygram.tecnical.test.dto.BookRequest;
import com.moneygram.tecnical.test.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "title", source = "request.title"),
        @Mapping(target = "author", source = "request.author"),
        @Mapping(target = "isbn", source = "request.isbn"),
        @Mapping(target = "publishedYear", source = "request.publishedYear"),
        @Mapping(target = "genre", source = "request.genre")
    })
    Book toBook(BookRequest request);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "title", source = "request.title"),
        @Mapping(target = "author", source = "request.author"),
        @Mapping(target = "isbn", source = "request.isbn"),
        @Mapping(target = "publishedYear", source = "request.publishedYear"),
        @Mapping(target = "genre", source = "request.genre")
    })
    Book toBook(Long id, BookRequest request);
}
