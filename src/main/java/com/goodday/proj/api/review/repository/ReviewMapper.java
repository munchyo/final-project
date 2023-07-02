package com.goodday.proj.api.review.repository;

import com.goodday.proj.api.review.model.Review;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReviewMapper extends ReviewRepository {
    @Override
    Optional<Review> findByOrderNo(Long orderNo);

    @Override
    int saveReviewAndImages(Review review);

    @Override
    int deleteReview(Long orderNo);

    @Override
    List<Review> findByProNo(Long proNo);
}
