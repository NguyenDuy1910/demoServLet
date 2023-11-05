package com.example.demo112.service;

import com.example.demo112.dtos.OrderDetailDTO;
import com.example.demo112.exceptions.DataNotFoundException;
import com.example.demo112.models.OrderDetail;

import java.util.List;

public interface IOrderDetailService {
    OrderDetail createOrderDetail(OrderDetailDTO newOrderDetail) throws Exception;
//    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;
//    OrderDetail updateOrderDetail(Long id, OrderDetailDTO newOrderDetailData)
//            throws DataNotFoundException;
//    void deleteById(Long id);
//    List<OrderDetail> findByOrderId(Long orderId);
}
