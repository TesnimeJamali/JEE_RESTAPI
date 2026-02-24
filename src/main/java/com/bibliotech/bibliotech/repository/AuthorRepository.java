package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}