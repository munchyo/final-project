package com.goodday.proj.api.shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFormDto {
    Long memberNo;

    @NotEmpty
    String proName;

    @NotEmpty
    String proContent;

    @Min(value = 0)
    @NotNull
    Integer proPrice;

    @Min(value = 0)
    @NotNull
    Integer proInventory;

    MultipartFile thumbnail;
    List<MultipartFile> images;
}
