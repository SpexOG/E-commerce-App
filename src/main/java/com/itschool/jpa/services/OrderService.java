package com.itschool.jpa.services;

import com.itschool.jpa.models.Order;
import com.itschool.jpa.models.OrderItem;

import java.util.List;

public interface OrderService {
    Order placeOrder(Long userId, List<OrderItem> items);

    List<Order> getOrdersWithPagination(Long userId, int page, int size);

    Order cancelOrder(Long orderId);
}
