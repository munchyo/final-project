package com.goodday.proj.api.review.repository;

import com.goodday.proj.api.review.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Optional<Review> findByOrderNo(Long orderNo);

    int saveReviewAndImages(Review review);

    int deleteReview(Long orderNo);

    List<Review> findByProNo(Long proNo);
}
