package com.goodday.proj.api.shop.service;

import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.pagination.PageInfo;
import com.goodday.proj.api.pagination.Pagination;
import com.goodday.proj.api.shop.dto.ProductFormDto;
import com.goodday.proj.api.shop.model.Product;
import com.goodday.proj.api.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Override
    public Map<String, Object> pageAndProductList(Integer currentPage) {
        int listCount = shopRepository.countProductList();
        PageInfo pageInfo = Pagination.getPageInfo(currentPage, listCount, 20);

        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pageInfo.getBoardLimit());
        ArrayList<Product> products = shopRepository.selectProductList(rowBounds);

        Map<String, Object> pageAndProductList = new HashMap<>();
        pageAndProductList.put("pageInfo", pageInfo);
        pageAndProductList.put("products", products);

        return pageAndProductList;
    }

    @Override
    public int writeProduct(UploadFile thumbnail, List<UploadFile> images, ProductFormDto form) {
        Product product = new Product();
        Long productNo = createProductNo();
        product.setProNo(productNo);
        product.setProName(form.getProName());
        product.setProContent(form.getProContent());
        product.setProPrice(form.getProPrice());
        product.setProInventory(form.getProInventory());
        product.setThumbnail(thumbnail);
        int save = shopRepository.save(product);

        Map map = new HashMap();
        map.put("images", images);
        map.put("proNo", productNo);
        int saveImages = shopRepository.saveImages(map);
        log.debug("save, saveImages : {}, {}",saveImages,save);
        return save;
    }

    private Long createProductNo() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
        return Long.parseLong(currentDateTime.format(formatter));
    }
}
