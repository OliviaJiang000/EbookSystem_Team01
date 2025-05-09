package com.example.demo.ServiceTest;

import com.example.demo.entity.Book;
import com.example.demo.entity.BorrowRecord;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.BorrowRecordRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;
import com.example.demo.service.impl.BorrowServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BorrowServiceTest {

    @InjectMocks
    private BorrowServiceImpl borrowService;

    @Mock
    private BorrowRecordRepository borrowRecordRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBorrowBookSuccess() {
        int userId = 1;
        int bookId = 100;

        when(borrowRecordRepository.findByUser_IdAndIsReturnedFalse(userId))
                .thenReturn(new ArrayList<>());

        when(borrowRecordRepository.existsByUser_IdAndBook_BookIdAndIsReturnedFalse(userId, bookId))
                .thenReturn(false);

        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));

        Book book = new Book();
        book.setBookId(bookId);
        book.setTitle("Test Book");
        book.setLeftCopies(3);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        when(borrowRecordRepository.save(any(BorrowRecord.class))).thenReturn(new BorrowRecord());

        borrowService.borrowBook(userId, bookId);

        verify(bookRepository).save(any(Book.class));
        verify(borrowRecordRepository).save(any(BorrowRecord.class));
        verify(emailService).sendBorrowConfirmation(eq("test@example.com"), eq("Test Book"));
    }

    @Test
    public void testBorrowBookWhenBookAlreadyBorrowed() {
        int userId = 1;
        int bookId = 100;

        when(borrowRecordRepository.findByUser_IdAndIsReturnedFalse(userId)).thenReturn(List.of(new BorrowRecord()));
        when(borrowRecordRepository.existsByUser_IdAndBook_BookIdAndIsReturnedFalse(userId, bookId)).thenReturn(true);

        assertThatThrownBy(() -> borrowService.borrowBook(userId, bookId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("already borrowed");
    }

    @Test
    public void testReturnBookSuccess() {
        int userId = 1;
        int bookId = 100;

        BorrowRecord record = new BorrowRecord();
        record.setBookId(bookId);
        record.setIsReturned(false);

        List<BorrowRecord> active = new ArrayList<>();
        active.add(record);
        when(borrowRecordRepository.findByUser_IdAndIsReturnedFalse(userId)).thenReturn(active);

        User user = new User();
        user.setEmail("test@example.com");
        when(userRepository.findById((long) userId)).thenReturn(Optional.of(user));

        Book book = new Book();
        book.setTitle("Test Book");
        book.setLeftCopies(1);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        borrowService.returnBook(userId, bookId);

        verify(borrowRecordRepository).save(record);
        verify(bookRepository).save(book);
        verify(emailService).sendReturnConfirmation("test@example.com", "Test Book");
    }

    @Test
    public void testGetBorrowHistory() {
        int userId = 1;
        List<BorrowRecord> mockList = List.of(new BorrowRecord());
        when(borrowRecordRepository.findByUser_Id(userId)).thenReturn(mockList);

        List<BorrowRecord> result = borrowService.getBorrowHistory(userId);
        assertThat(result).isEqualTo(mockList);
    }

    @Test
    public void testGetAllBorrowRecords() {
        List<BorrowRecord> records = List.of(new BorrowRecord());
        when(borrowRecordRepository.findAll()).thenReturn(records);

        List<BorrowRecord> result = borrowService.getAllBorrowRecords();
        assertThat(result).isEqualTo(records);
    }
}
