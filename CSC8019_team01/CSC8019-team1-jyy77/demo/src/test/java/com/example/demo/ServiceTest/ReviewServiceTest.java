package com.example.demo.ServiceTest;

import com.example.demo.entity.ReviewRecord;
import com.example.demo.repository.ReviewRecordRepository;
import com.example.demo.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ReviewServiceTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Mock
    private ReviewRecordRepository reviewRecordRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddReview() {
        ReviewRecord review = new ReviewRecord();
        review.setUserId(1);
        review.setBookId(101);
        review.setRating(5);
        review.setComment("Great book!");

        // 执行方法
        reviewService.addReview(1, 101, 5, "Great book!");

        // 验证调用
        verify(reviewRecordRepository, times(1)).save(argThat(saved -> 
            saved.getUserId() == 1 &&
            saved.getBookId() == 101 &&
            saved.getRating() == 5 &&
            "Great book!".equals(saved.getComment())
        ));
    }

    @Test
    public void testGetReviewsByBook() {
        ReviewRecord review = new ReviewRecord();
        review.setBookId(101);
        review.setComment("Excellent!");

        when(reviewRecordRepository.findByBookId(101)).thenReturn(List.of(review));

        List<ReviewRecord> results = reviewService.getReviewsByBook(101);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getComment()).isEqualTo("Excellent!");
        verify(reviewRecordRepository, times(1)).findByBookId(101);
    }
}
