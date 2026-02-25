package com.bibliotech.bibliotech.mapper;

import com.bibliotech.bibliotech.dto.BookDTO;
import com.bibliotech.bibliotech.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(source = "author.name", target = "authorName")
    BookDTO toDTO(Book book);
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Book toEntity(BookDTO dto);
}