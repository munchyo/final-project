package com.goodday.proj.api.shop.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductFormDto {
    String proName;
    String proContent;
    Integer proPrice;
    Integer proInventory;
    MultipartFile thumbnail;
    List<MultipartFile> images;
}
