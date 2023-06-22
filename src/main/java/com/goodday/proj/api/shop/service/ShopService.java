package com.goodday.proj.api.shop.service;

import com.goodday.proj.api.shop.model.Product;

import java.util.ArrayList;
import java.util.Map;

public interface ShopService {

    Map<String, Object> pageAndProductList(Integer currentPage);
}
