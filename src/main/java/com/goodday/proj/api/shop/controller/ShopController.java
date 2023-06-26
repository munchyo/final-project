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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
public class ShopController {

    private final ShopService shopService;
    private final ShopRepository shopRepository;
    private final FileStore fileStore;

    /**
     * 상품목록, 페이지네이션
     * @param currentPage
     * @return
     */
    @GetMapping
    public Map<String, Object> product(@RequestParam(required = false) Integer currentPage) {
        if (currentPage == null || currentPage < 0) {
            currentPage = 1;
        }
        return shopService.pageAndProductList(currentPage);
    }

    /**
     * 상품 등록
     * @param form
     * @throws IOException
     */
    @PostMapping("/write")
    public void writeProduct(@ModelAttribute ProductFormDto form) throws IOException {
        UploadFile thumbnail = fileStore.storeFile(form.getThumbnail());
        List<UploadFile> images = fileStore.storeFiles(form.getImages());

        shopService.writeProduct(thumbnail, images, form);
    }

    /**
     * 상품 상세보기
     * @param proNo
     * @return Product
     */
    @GetMapping("/{proNo}")
    public Product viewProduct(@PathVariable Long proNo) {
        return shopRepository.findByNo(proNo);
    }

    /**
     * 상품 수정 view
     * @param proNo
     * @return Product
     */
    @GetMapping("/edit/{proNo}")
    public Product editProduct(@PathVariable Long proNo) {
        return shopRepository.findByNo(proNo);
    }

    /**
     * 상품 수정
     * @param proNo
     * @param form
     * @throws IOException
     */
    @PatchMapping("/{proNo}")
    public void updateProduct(@PathVariable Long proNo, @ModelAttribute ProductFormDto form) throws IOException {
        UploadFile thumbnail = fileStore.storeFile(form.getThumbnail());
        List<UploadFile> images = fileStore.storeFiles(form.getImages());

        shopService.writeProduct(thumbnail, images, form);
    }

    /**
     * 상품 삭제
     * @param proNo
     */
    @DeleteMapping("/{proNo}")
    public void deleteProduct(@PathVariable Long proNo) {
        Product product = shopRepository.findByNo(proNo);
        fileStore.deleteFile(product.getThumbnail().getStoreFileName());
        product.getImages().stream().forEach(uploadFile -> fileStore.deleteFile(uploadFile.getStoreFileName()));

        shopRepository.deleteProductByNo(proNo);
        shopRepository.deleteFileByNo(proNo);
    }
}
