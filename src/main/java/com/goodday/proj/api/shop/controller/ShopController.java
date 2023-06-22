package com.goodday.proj.api.shop.controller;

import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.pagination.PageInfo;
import com.goodday.proj.api.pagination.Pagination;
import com.goodday.proj.api.shop.dto.ProductFormDto;
import com.goodday.proj.api.shop.model.Product;
import com.goodday.proj.api.shop.repository.ShopRepository;
import com.goodday.proj.api.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final ShopRepository shopRepository;
    private final FileStore fileStore;

    @GetMapping
    public Map<String, Object> product(@RequestParam(required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 0) {
            currentPage = 1;
        }
        return shopService.pageAndProductList(currentPage);
    }

    /**
     * TODO
     * productNo는 내가직접만들어야될듯 sequence 사용 X
    */
     @PostMapping("/write")
    public void writeProduct(@RequestBody ProductFormDto form) throws IOException {
         UploadFile thumbnail = fileStore.storeFile(form.getThumbnail());
         List<UploadFile> images = fileStore.storeFiles(form.getImages());
         shopService.writeProduct(thumbnail, images, form);
     }
}
