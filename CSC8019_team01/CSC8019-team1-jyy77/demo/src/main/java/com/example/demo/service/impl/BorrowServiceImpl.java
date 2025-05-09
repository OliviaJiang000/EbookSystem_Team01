package com.example.demo.service.impl;

import com.example.demo.entity.BorrowRecord;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.repository.BorrowRecordRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BorrowService;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of the BorrowService interface.
 * Manages the logic for borrowing and returning books, including validation,
 * inventory updates, and sending confirmation emails.
 *
 * Author: Guanyuan Wang
 */
@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Borrows a book for a user if allowed. Validates borrow limits, book availability,
     * and prevents duplicate unreturned borrowings. Updates book inventory and saves the record.
     * Sends a confirmation email upon success.
     *
     * @param userId the ID of the user borrowing the book
     * @param bookId the ID of the book to borrow
     */
    @Override
    public void borrowBook(int userId, int bookId) {
        // Check if the user has already borrowed 10 books
        int currentBorrowCount = borrowRecordRepository
                .findByUser_IdAndIsReturnedFalse(userId).size();
        if (currentBorrowCount >= 10) {
            throw new RuntimeException("You can borrow at most 10 books.");
        }

        // Prevent borrowing the same book twice before returning
        boolean alreadyBorrowed = borrowRecordRepository
                .existsByUser_IdAndBook_BookIdAndIsReturnedFalse(userId, bookId);
        if (alreadyBorrowed) {
            throw new RuntimeException("You have already borrowed this book and haven't returned it.");
        }

        // Retrieve the user entity
        User user = userRepository.findById((long) userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        // Retrieve the book and check availability
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found."));
        if (book.getLeftCopies() <= 0) {
            throw new RuntimeException("No more copies available for this book.");
        }

        // Decrease the number of available copies
        book.setLeftCopies(book.getLeftCopies() - 1);
        bookRepository.save(book);

        // Create and save the borrow record
        BorrowRecord record = new BorrowRecord();
        record.setUserId((long) userId);
        record.setBookId(bookId);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setIsReturned(false);
        BorrowRecord savedRecord = borrowRecordRepository.save(record);

        // Send confirmation email
        emailService.sendBorrowConfirmation(user.getEmail(), book.getTitle());
    }

    /**
     * Returns a book previously borrowed by the user. Marks the record as returned,
     * updates the inventory, and sends a return confirmation email.
     *
     * @param userId the ID of the user returning the book
     * @param bookId the ID of the book being returned
     */
    @Override
    public void returnBook(int userId, int bookId) {
        List<BorrowRecord> records = borrowRecordRepository.findByUser_IdAndIsReturnedFalse(userId);

        // Retrieve the user entity
        User user = userRepository.findById((long) userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        for (BorrowRecord record : records) {
            if (record.getBookId() == bookId) {
                record.setIsReturned(true);
                borrowRecordRepository.save(record);

                // Increase the number of available copies
                Book book = bookRepository.findById(bookId)
                        .orElseThrow(() -> new RuntimeException("Book not found."));
                book.setLeftCopies(book.getLeftCopies() + 1);
                bookRepository.save(book);

                // Send confirmation email
                emailService.sendReturnConfirmation(user.getEmail(), book.getTitle());
                return;
            }
        }

        throw new RuntimeException("No active borrow record found for this book and user.");
    }

    /**
     * Retrieves all borrow records for a specific user.
     *
     * @param userId the ID of the user
     * @return list of BorrowRecord objects
     */
    @Override
    public List<BorrowRecord> getBorrowHistory(int userId) {
        return borrowRecordRepository.findByUser_Id(userId);
    }

    /**
     * Retrieves all borrow records in the system.
     *
     * @return list of all BorrowRecord objects
     */
    @Override
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }
}
