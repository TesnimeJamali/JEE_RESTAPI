package com.bibliotech.bibliotech.service;


import com.bibliotech.bibliotech.mapper.BookMapper;
import com.bibliotech.bibliotech.dto.BookDTO;
import com.bibliotech.bibliotech.model.Book;
import com.bibliotech.bibliotech.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO saveBook(BookDTO dto) {
        Book book = bookMapper.toEntity(dto);
        Book saved = bookRepository.save(book);
        return bookMapper.toDTO(saved);
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id " + id));
        return bookMapper.toDTO(book);
    }
}