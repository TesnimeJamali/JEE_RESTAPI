package com.bibliotech.bibliotech.controller;

import com.bibliotech.bibliotech.model.BookDocument;
import com.bibliotech.bibliotech.repository.BookReactiveRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class BookReactiveController {

    private final BookReactiveRepository repository;

    public BookReactiveController(BookReactiveRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/reactive/books")
    public Flux<BookDocument> getAllBooks() {
        return repository.findAll();
    }
}