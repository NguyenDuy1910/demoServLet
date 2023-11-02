package com.example.demo112.service;

import com.example.demo112.dtos.OrderDetailDTO;
import com.example.demo112.models.Order;
import com.example.demo112.models.OrderDetail;
import com.example.demo112.repositories.OrderDetailRepository;
import com.example.demo112.repositories.OrderRepository;
import com.example.demo112.repositories.ProductRepository;
import com.sun.org.apache.xpath.internal.operations.Or;

public class OrderDetailService implements IOrderDetailService{
    private final OrderRepository orderRepository=new OrderRepository();
    private final OrderDetailRepository orderDetailRepository=new OrderDetailRepository();
    private final ProductRepository productRepository=new ProductRepository();
public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception{
    Order order=orderRepository.findById(orderDetailDTO.getOrderId());

}

}
