package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.model.Book;
import com.bibliotech.bibliotech.model.Borrowing;
import com.bibliotech.bibliotech.model.BorrowingStatus;
import com.bibliotech.bibliotech.repository.BookRepository;
import com.bibliotech.bibliotech.repository.BorrowingRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Service
@RequiredArgsConstructor
public class BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;

    @Transactional
    public Borrowing checkout(Long bookId, Long userId) {

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        if (book.getStock() <= 0) {
            throw new IllegalStateException("Book out of stock");
        }

        long activeBorrowings = borrowingRepository
                .countByUserIdAndStatus(userId, BorrowingStatus.ONGOING);

        if (activeBorrowings >= 3) {
            throw new IllegalStateException("Maximum borrowing limit reached");
        }

        book.setStock(book.getStock() - 1);

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setUserId(userId);
        borrowing.setBorrowDate(LocalDate.now());
        borrowing.setStatus(BorrowingStatus.ONGOING);

        return borrowingRepository.save(borrowing);
    }

    @Transactional
    public Borrowing returnBook(Long borrowingId) {

        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new IllegalArgumentException("Borrowing not found"));

        borrowing.setStatus(BorrowingStatus.RETURNED);
        borrowing.setReturnDate(LocalDate.now());

        Book book = borrowing.getBook();
        book.setStock(book.getStock() + 1);

        return borrowingRepository.save(borrowing);
    }
    public List<Borrowing> getUserBorrowings(Long userId) {
        return borrowingRepository.findByUserId(userId);
    }

    @ExtendWith(MockitoExtension.class)
    static
    class BorrowingServiceTest {

        @Mock
        private BookRepository bookRepository;

        @Mock
        private BorrowingRepository borrowingRepository;

        @InjectMocks
        private BorrowingService borrowingService;

        @Test
        void shouldBorrowBookSuccessfully() {

            Book book = new Book();
            book.setId(1L);
            book.setStock(2);

            when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
            when(borrowingRepository.countByUserIdAndStatus(1L, BorrowingStatus.ONGOING))
                    .thenReturn(0L);

            borrowingService.checkout(1L, 1L);

            assertEquals(1, book.getStock());
            verify(borrowingRepository, times(1)).save(any(Borrowing.class));
        }

        @Test
        void shouldThrowExceptionWhenStockIsZero() {

            Book book = new Book();
            book.setId(1L);
            book.setStock(0);

            when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

            assertThrows(IllegalStateException.class,
                    () -> borrowingService.checkout(1L, 1L));
        }
    }
}