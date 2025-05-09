package com.example.demo.EntityTest;

import com.example.demo.entity.BorrowRecord;
import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class BorrowRecordTest {

    @Test
    public void testBorrowRecordProperties() {
        BorrowRecord record = new BorrowRecord();

        // Set value
        record.setBorrowRecordId(1);
        record.setUserId(100L);
        record.setBookId(200);
        record.setBorrowDate(LocalDate.of(2024, 5, 1));
        record.setDueDate(LocalDate.of(2024, 5, 15));
        record.setIsReturned(true);

        // Verification result
        assertThat(record.getBorrowRecordId()).isEqualTo(1);
        assertThat(record.getUserId()).isEqualTo(100L);
        assertThat(record.getBookId()).isEqualTo(200);
        assertThat(record.getBorrowDate()).isEqualTo(LocalDate.of(2024, 5, 1));
        assertThat(record.getDueDate()).isEqualTo(LocalDate.of(2024, 5, 15));
        assertThat(record.getIsReturned()).isTrue();

        // Verify that the associated object is not empty
        assertThat(record.getUser()).isInstanceOf(User.class);
        assertThat(record.getBook()).isInstanceOf(Book.class);
    }
}
