package com.goodday.proj.api.shop.service;

import com.goodday.proj.api.pagination.PageInfo;
import com.goodday.proj.api.pagination.Pagination;
import com.goodday.proj.api.shop.model.Product;
import com.goodday.proj.api.shop.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
}
