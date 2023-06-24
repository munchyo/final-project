package com.goodday.proj.api.shop.controller;

import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.shop.dto.ProductFormDto;
import com.goodday.proj.api.shop.model.Product;
import com.goodday.proj.api.shop.repository.ShopRepository;
import com.goodday.proj.api.shop.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    @PostMapping("/write")
    public void writeProduct(@ModelAttribute ProductFormDto form) throws IOException {
        UploadFile thumbnail = fileStore.storeFile(form.getThumbnail());
        List<UploadFile> images = fileStore.storeFiles(form.getImages());

        shopService.writeProduct(thumbnail, images, form);
    }

    // TODO 상세보기 - 이미지 경로
    @GetMapping("/{proNo}")
    public Product viewProduct(@PathVariable Long proNo) {
        Optional<Product> product = shopRepository.findByNo(proNo);

        return null;
    }

}
