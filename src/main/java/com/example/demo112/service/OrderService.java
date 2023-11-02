package com.example.demo112.service;

import com.example.demo112.dtos.OrderDTO;
import com.example.demo112.exceptions.DataNotFoundException;
import com.example.demo112.models.Order;
import com.example.demo112.models.User;
import com.example.demo112.repositories.OrderDetailRepository;
import com.example.demo112.repositories.OrderRepository;
import com.example.demo112.repositories.ProductRepository;
import com.example.demo112.repositories.UserRepository;

public class OrderService implements IOrderService {
    private final UserRepository userRepository=new UserRepository();
    private final OrderRepository orderRepository=new OrderRepository();
    private final ProductRepository productRepository=new ProductRepository();
    private final OrderDetailRepository orderDetailRepository=new OrderDetailRepository();
@Override
public Order createOrder(OrderDTO orderDTO) throws Exception{
    User use=userRepository.findById(orderDTO.getUserId()) ;



}

}
