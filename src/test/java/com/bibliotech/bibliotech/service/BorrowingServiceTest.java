package com.bibliotech.bibliotech.service;

import com.bibliotech.bibliotech.model.Book;
import com.bibliotech.bibliotech.model.Borrowing;
import com.bibliotech.bibliotech.model.BorrowingStatus;
import com.bibliotech.bibliotech.repository.BookRepository;
import com.bibliotech.bibliotech.repository.BorrowingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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