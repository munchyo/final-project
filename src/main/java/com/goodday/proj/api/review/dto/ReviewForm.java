package com.goodday.proj.api.review.dto;

import com.goodday.proj.api.file.model.UploadFile;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewForm {
    @NotNull
    Long orderNo;

    @NotEmpty
    String reviewTitle;

    @NotEmpty
    String reviewContent;

    @Max(value = 5)
    @Min(value = 1)
    @NotNull
    Double reviewScore;

    List<MultipartFile> images;
}
