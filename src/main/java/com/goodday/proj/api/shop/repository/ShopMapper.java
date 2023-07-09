package com.goodday.proj.api.shop.repository;

import com.goodday.proj.api.file.model.UploadFile;
import com.goodday.proj.api.shop.model.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ShopMapper extends ShopRepository {

    @Override
    int countProductList();

    @Override
    ArrayList<Product> selectProductList(RowBounds rowBounds);

    @Override
    int save(Product product);

    @Override
    int saveImages(Map images);

    @Override
    Product findByNo(Long proNo);

    @Override
    int deleteProductByNo(Long proNo);

    @Override
    int deleteFileByNo(Long proNo);

    @Override
    int updateFileByStoreFileName(Map<String, String> updateFile);

    @Override
    int updateProduct(Product editProduct);

    @Override
    int countProductListByProduct(String product);

    @Override
    ArrayList<Product> selectProductListByProduct(String product, RowBounds rowBounds);
}
