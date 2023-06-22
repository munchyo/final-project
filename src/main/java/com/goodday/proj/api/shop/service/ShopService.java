package com.goodday.proj.api.shop.service;

import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.shop.dto.ProductFormDto;

import java.util.List;
import java.util.Map;

public interface ShopService {

    Map<String, Object> pageAndProductList(Integer currentPage);

    int writeProduct(UploadFile thumbnail, List<UploadFile> images, ProductFormDto form);
}
