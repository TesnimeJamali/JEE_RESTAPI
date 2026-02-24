package com.bibliotech.bibliotech.repository;


import com.bibliotech.bibliotech.model.BookDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BookReactiveRepository extends ReactiveMongoRepository<BookDocument, String> {

    Flux<BookDocument> findByAuthorName(String authorName);
}