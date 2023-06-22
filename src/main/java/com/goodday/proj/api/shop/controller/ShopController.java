package com.goodday.proj.api.shop.controller;

import com.goodday.proj.api.pagination.PageInfo;
import com.goodday.proj.api.pagination.Pagination;
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

    @Value("${file.dir}")
    private String fileDir;

    private final String wallPaperDir = System.getProperty("user.home");

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
    public void writeProduct(@RequestParam String proName, @RequestParam String proContent, @RequestParam int proPrice,
                             @RequestParam int proInventory, @RequestParam MultipartFile thumbNail,
                             @RequestParam List<MultipartFile> images) throws IOException {
        images.stream().filter(file -> images.isEmpty());
        if (!thumbNail.isEmpty()) {
            String fullPath = wallPaperDir + fileDir + thumbNail.getOriginalFilename();
            thumbNail.transferTo(new File(fullPath));
        }

        if (!images.isEmpty()) {
            for (MultipartFile image : images) {
                String fullPath = wallPaperDir + fileDir + thumbNail.getOriginalFilename();
                thumbNail.transferTo(new File(fullPath));
            }
        }

    }
}
