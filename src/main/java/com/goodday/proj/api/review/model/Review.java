package com.goodday.proj.api.review.model;

import com.goodday.proj.api.file.model.UploadFile;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {

    long orderNo;
    Long memberNo;
    String nickname;
    String reviewTitle;
    String reviewContent;
    double reviewScore;
    Date reviewCreateDate;
    List<UploadFile> images;

    public Review(long orderNo, String reviewTitle, String reviewContent, double reviewScore, List<UploadFile> images) {
        this.orderNo = orderNo;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewScore = reviewScore;
        this.images = images;
    }
}
