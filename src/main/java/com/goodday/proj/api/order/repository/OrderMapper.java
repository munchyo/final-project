package com.goodday.proj.api.order.repository;

import com.goodday.proj.api.order.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper extends OrderRepository {
    @Override
    int saveOrder(Map<String, Object> addOrder);

    @Override
    int countMyOrderListByMemberNo(Long memberNo);

    @Override
    List<Order> findMyOrderListByMemberNo(Long memberNo, RowBounds rowBounds);

    @Override
    Order findOrderByOrderNo(Long orderNo);

    @Override
    int updateInventory(Map<String, Object> updateInventoryMap);
}
