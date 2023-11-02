package com.example.demo112.service;

import com.example.demo112.dtos.OrderDTO;
import com.example.demo112.exceptions.DataNotFoundException;
import com.example.demo112.models.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;
//    Order getOrder(Long id);
//    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;
//    void deleteOrder(Long id);
//    List<Order> findByUserId(Long userId);
}
