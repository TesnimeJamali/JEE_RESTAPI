package com.bibliotech.bibliotech.controller;


import com.bibliotech.bibliotech.model.Borrowing;
import com.bibliotech.bibliotech.service.BorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/borrowings")
@RequiredArgsConstructor
public class BorrowingController {

    private final BorrowingService borrowingService;

    @PostMapping("/checkout")
    public ResponseEntity<Borrowing> checkout(@RequestParam Long bookId, @RequestParam Long userId) {
        Borrowing borrowing = borrowingService.checkout(bookId, userId);
        return ResponseEntity.ok(borrowing);
    }

    @PostMapping("/return/{borrowingId}")
    public ResponseEntity<Borrowing> returnBook(@PathVariable Long borrowingId) {
        Borrowing borrowing = borrowingService.returnBook(borrowingId);
        return ResponseEntity.ok(borrowing);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Borrowing>> getUserBorrowings(@PathVariable Long userId) {
        List<Borrowing> borrowings = borrowingService.getUserBorrowings(userId);
        return ResponseEntity.ok(borrowings);
    }
}