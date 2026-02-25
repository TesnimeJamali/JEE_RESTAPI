package com.bibliotech.bibliotech.service;


import com.bibliotech.bibliotech.mapper.BookMapper;
import com.bibliotech.bibliotech.dto.BookDTO;
import com.bibliotech.bibliotech.model.Author;
import com.bibliotech.bibliotech.model.Book;
import com.bibliotech.bibliotech.repository.BookRepository;
import com.bibliotech.bibliotech.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository; // ← add this

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    public BookDTO saveBook(BookDTO dto) {
        if (bookRepository.findByIsbn(dto.getIsbn()).isPresent()) {
            throw new IllegalStateException("ISBN already exists");
        }
        Book book = bookMapper.toEntity(dto);
        Book saved = bookRepository.save(book);
        return bookMapper.toDTO(saved);
    }

    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id " + id));
        return bookMapper.toDTO(book);
    }
    public BookDTO updateBook(Long id, BookDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Update fields
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setStock(dto.getStock());

        // Update author safely
        if (book.getAuthor() == null || !book.getAuthor().getName().equals(dto.getAuthorName())) {
            Author author = authorRepository.findByName(dto.getAuthorName())
                    .orElseGet(() -> {
                        Author newAuthor = new Author();
                        newAuthor.setName(dto.getAuthorName());
                        return authorRepository.save(newAuthor);
                    });
            book.setAuthor(author);
        }

        Book saved = bookRepository.save(book);

        // Map back to DTO
        BookDTO result = new BookDTO();
        result.setId(saved.getId());
        result.setTitle(saved.getTitle());
        result.setIsbn(saved.getIsbn());
        result.setAuthorName(saved.getAuthor().getName());
        result.setStock(saved.getStock());
        return result;
    }
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        bookRepository.delete(book);
    }
}