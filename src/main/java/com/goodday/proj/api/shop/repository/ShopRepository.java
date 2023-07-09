package com.goodday.proj.api.shop.repository;

import com.goodday.proj.api.shop.model.Product;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.Map;

public interface ShopRepository {

    int countProductList();

    ArrayList<Product> selectProductList(RowBounds rowBounds);

    int save(Product product);

    Product findByNo(Long proNo);

    int saveImages(Map images);

    int deleteProductByNo(Long proNo);

    int deleteFileByNo(Long proNo);

    int updateFileByStoreFileName(Map<String, String> updateFile);

    int updateProduct(Product editProduct);

    int countProductListByProduct(String product);

    ArrayList<Product> selectProductListByProduct(String product, RowBounds rowBounds);
}
