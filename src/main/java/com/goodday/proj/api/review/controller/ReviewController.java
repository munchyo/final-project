package com.goodday.proj.api.review.controller;

import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.file.repository.FileRepository;
import com.goodday.proj.api.review.dto.ReviewForm;
import com.goodday.proj.api.review.model.Review;
import com.goodday.proj.api.review.repository.ReviewRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewRepository reviewRepository;
    private final FileStore fileStore;
    private final FileRepository fileRepository;

    @PostMapping
    public void writeReview(@Valid @ModelAttribute ReviewForm form, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        if (reviewRepository.findByOrderNo(form.getOrderNo()).isPresent()) {
            throw new RuntimeException("이미 리뷰가 존재합니다.");
        }

        List<UploadFile> uploadFiles = fileStore.storeFiles(form.getImages());
        Review review = new Review(form.getOrderNo(), form.getReviewTitle(), form.getReviewContent(), form.getReviewScore(), uploadFiles);
        int result = reviewRepository.saveReviewAndImages(review);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }

    @DeleteMapping
    public void deleteReview(@RequestParam Long orderNo) {
        Review review = reviewRepository.findByOrderNo(orderNo).get();
        review.getImages().stream()
                .forEach(uploadFile -> fileStore.deleteFile(uploadFile.getStoreFileName()));
        review.getImages().stream()
                .forEach(uploadFile -> fileRepository.deleteFile(uploadFile.getStoreFileName()));
        int result = reviewRepository.deleteReview(orderNo);

        if (result == 0) {
            throw new RuntimeException(ErrorConst.deleteError);
        }
    }
}
