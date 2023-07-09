package com.goodday.proj.api.shop.controller;

import com.goodday.proj.annotation.AuthChecker;
import com.goodday.proj.constant.ErrorConst;
import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.member.repository.MemberRepository;
import com.goodday.proj.api.shop.dto.ProductFormDto;
import com.goodday.proj.api.shop.model.Product;
import com.goodday.proj.api.shop.repository.ShopRepository;
import com.goodday.proj.api.shop.service.ShopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
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
    private final MemberRepository memberRepository;
    private final FileStore fileStore;

    /**
     * 상품목록, 페이지네이션
     * @param currentPage
     * @return
     */
    @GetMapping
    public Map<String, Object> product(@RequestParam(value = "page", required = false) Integer currentPage,
                                       @RequestParam(required = false) String product){
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (product == null) {
            product = "ALL";
        }
        return shopService.pageAndProductList(currentPage, product);
    }

    /**
     * 상품 등록
     *
     * @param form
     * @throws IOException
     */
    @AuthChecker
    @PostMapping("/write")
    public void writeProduct(@Valid @ModelAttribute ProductFormDto form, BindingResult bindingResult) throws IOException {
        if (memberRepository.findSessionMemberByNo(form.getMemberNo()).get().getAdmin().equals("N")) {
            throw new RuntimeException(ErrorConst.authError);
        }
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }
        UploadFile thumbnail = fileStore.storeFile(form.getThumbnail());
        List<UploadFile> images = fileStore.storeFiles(form.getImages());

        int result = shopService.writeProduct(thumbnail, images, form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.insertError);
        }
    }

    /**
     * 상품 상세보기
     *
     * @param proNo
     * @return Product
     */
    @GetMapping("/{proNo}")
    public Map<String, Object> viewProduct(@PathVariable Long proNo) {
        return shopService.viewProduct(proNo);
    }

    /**
     * 상품 수정 view 이동
     * @param proNo
     * @return Product
     */
    @AuthChecker
    @PostMapping("/{proNo}")
    public Product editProduct(@PathVariable Long proNo) {
        return shopRepository.findByNo(proNo);
    }

    /**
     * 상품 수정
     *
     * @param proNo
     * @param form
     * @throws IOException
     */
    @AuthChecker
    @PostMapping("/{proNo}/edit")
    public void editProduct(@PathVariable Long proNo, @Valid @ModelAttribute ProductFormDto form,
                              BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(ErrorConst.bindingError);
        }

        int result = shopService.editProductAndFile(proNo, form);
        if (result == 0) {
            throw new RuntimeException(ErrorConst.updateError);
        }
    }

    /**
     * 상품 삭제
     *
     * @param proNo
     */
    @AuthChecker
    @DeleteMapping("/{proNo}")
    public void deleteProduct(@PathVariable Long proNo, @RequestParam Long memberNo) {
        Product product = shopRepository.findByNo(proNo);
        fileStore.deleteFile(product.getThumbnail().getStoreFileName());
        product.getImages().stream().forEach(uploadFile -> fileStore.deleteFile(uploadFile.getStoreFileName()));

        shopRepository.deleteProductByNo(proNo);
        shopRepository.deleteFileByNo(proNo);
    }
}
