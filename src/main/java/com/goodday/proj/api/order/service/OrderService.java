package com.goodday.proj.api.order.service;

import com.goodday.proj.api.order.dto.OrderDto;
import com.goodday.proj.api.order.dto.OrderForm;

import java.util.Map;

public interface OrderService {
    Map<String, Object> orderView(OrderDto orderDto);

    int order(OrderForm form);
}
