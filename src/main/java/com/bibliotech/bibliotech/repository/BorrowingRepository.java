package com.bibliotech.bibliotech.repository;

import com.bibliotech.bibliotech.model.Borrowing;
import com.bibliotech.bibliotech.model.BorrowingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    List<Borrowing> findByUserId(Long userId);

    long countByUserIdAndStatus(Long userId, BorrowingStatus status);
}