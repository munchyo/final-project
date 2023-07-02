package com.goodday.proj.api.order.repository;

import com.goodday.proj.api.order.model.Order;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;

public interface OrderRepository {
    int saveOrder(Map<String, Object> addOrder);

    int countMyOrderListByMemberNo(Long memberNo);

    List<Order> findMyOrderListByMemberNo(Long memberNo, RowBounds rowBounds);

    Order findOrderByOrderNo(Long orderNo);
}
