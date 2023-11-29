package com.example.demo112.service;

import com.example.demo112.dtos.OrderDTO;
import com.example.demo112.exceptions.DataNotFoundException;
import com.example.demo112.models.Order;
import com.example.demo112.responses.OrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IOrderService {
    Order createOrder(OrderDTO orderDTO) throws Exception;

    Order getOrder(Long id);

    Order updateOrder(Long id, OrderDTO orderDTO) throws Exception;
//    void deleteOrder(Long id);
//    List<Order> findByUserId(Long userId);
Page<Order> getOrdersByKeyword(String keyword, PageRequest pageRequest);

}
