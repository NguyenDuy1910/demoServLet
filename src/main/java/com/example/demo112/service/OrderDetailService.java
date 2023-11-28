package com.example.demo112.service;

import com.example.demo112.dtos.OrderDTO;
import com.example.demo112.dtos.OrderDetailDTO;
import com.example.demo112.models.Order;
import com.example.demo112.models.OrderDetail;
import com.example.demo112.models.Product;
import com.example.demo112.repositories.OrderDetailRepository;
import com.example.demo112.repositories.OrderRepository;
import com.example.demo112.repositories.ProductRepository;

import java.util.List;

public class OrderDetailService implements IOrderDetailService {
    private final OrderRepository orderRepository = new OrderRepository();
    private final OrderDetailRepository orderDetailRepository = new OrderDetailRepository();
    private final ProductRepository productRepository = new ProductRepository();

    public OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception {
        Order order = orderRepository.findById(orderDetailDTO.getOrderId());
        Product product = productRepository.findProductById(orderDetailDTO.getProductId());
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setPrice(orderDetailDTO.getPrice());
        orderDetail.setNumberOfProducts(orderDetailDTO.getNumberOfProducts());
        orderDetail.setTotalMoney(orderDetailDTO.getTotalMoney());
        orderDetail.setColor(orderDetailDTO.getColor());
        return orderDetailRepository.save(orderDetail);
    }

    public List<OrderDetail> findByOrderId(Long orderId) {
        return orderDetailRepository.findByOrderId(orderId);
    }


}
