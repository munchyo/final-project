package com.goodday.proj.api.shop.service;

import com.goodday.proj.api.file.FileStore;
import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.pagination.model.PageInfo;
import com.goodday.proj.api.pagination.Pagination;
import com.goodday.proj.api.shop.dto.ProductFormDto;
import com.goodday.proj.api.shop.model.Product;
import com.goodday.proj.api.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
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
    private final FileStore fileStore;

    private Long createProductNo() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
        return Long.parseLong(currentDateTime.format(formatter));
    }

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
        return save + saveImages;
    }

    @Override
    public int editProductAndFile(Long proNo, ProductFormDto form) throws IOException {
        int result = 0;
        if (!form.getThumbnail().getOriginalFilename().equals("")) {
            Product product = shopRepository.findByNo(proNo);
            fileStore.deleteFile(product.getThumbnail().getStoreFileName());

            UploadFile uploadFile = fileStore.storeFile(form.getThumbnail());

            Map<String, String> updateFile = new HashMap<>();
            updateFile.put("afterStoreFileName", product.getThumbnail().getStoreFileName());
            updateFile.put("uploadFileName", uploadFile.getUploadFileName());
            updateFile.put("storeFileName", uploadFile.getStoreFileName());
            result += shopRepository.updateFileByStoreFileName(updateFile);
        }
        if (form.getImages().stream()
                .filter(file -> !file.getOriginalFilename().equals("")).findAny().isPresent()) {

            List<UploadFile> images = fileStore.storeFiles(form.getImages());
            Map map = new HashMap();
            map.put("images", images);
            map.put("proNo", proNo);
            result += shopRepository.saveImages(map);
        }

        result += shopRepository.updateProduct(new Product
                (proNo, form.getProName(), form.getProContent(), form.getProPrice(), form.getProInventory()));
        return result;
    }
}
