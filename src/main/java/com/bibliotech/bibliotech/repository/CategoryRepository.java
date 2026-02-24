package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}