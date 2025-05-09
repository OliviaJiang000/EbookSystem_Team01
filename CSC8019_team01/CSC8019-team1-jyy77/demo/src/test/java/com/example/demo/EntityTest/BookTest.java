package com.example.demo.EntityTest;

import com.example.demo.entity.Book;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class BookTest {

    @Test
    public void testBookProperties() {
        Book obj = new Book();
        obj.setBookId(1);
        obj.setTitle("testTitle");

        assertThat(obj.getBookId()).isEqualTo(1);
        assertThat(obj.getTitle()).isEqualTo("testTitle");
    }
}
