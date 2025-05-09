package com.example.demo.EntityTest;

import com.example.demo.entity.ReviewRecord;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

public class ReviewRecordTest {

    @Test
    public void testReviewRecordProperties() {
        ReviewRecord obj = new ReviewRecord();
        LocalDateTime now = LocalDateTime.now();

        obj.setReviewRecordId(1);
        obj.setUserId(101);
        obj.setBookId(202);
        obj.setRating(5);
        obj.setComment("Excellent book!");
        obj.setCreatedAt(now);

        assertThat(obj.getReviewRecordId()).isEqualTo(1);
        assertThat(obj.getUserId()).isEqualTo(101);
        assertThat(obj.getBookId()).isEqualTo(202);
        assertThat(obj.getRating()).isEqualTo(5);
        assertThat(obj.getComment()).isEqualTo("Excellent book!");
        assertThat(obj.getCreatedAt()).isEqualTo(now);
    }
}
