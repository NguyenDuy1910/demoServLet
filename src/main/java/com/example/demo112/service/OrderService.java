package com.example.demo112.service;

import com.example.demo112.dtos.CartItemDTO;
import com.example.demo112.dtos.OrderDTO;
import com.example.demo112.exceptions.DataNotFoundException;
import com.example.demo112.models.Order;
import com.example.demo112.models.OrderDetail;
import com.example.demo112.models.Product;
import com.example.demo112.models.User;
import com.example.demo112.repositories.OrderDetailRepository;
import com.example.demo112.repositories.OrderRepository;
import com.example.demo112.repositories.ProductRepository;
import com.example.demo112.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderService implements IOrderService {
    private final UserRepository userRepository=new UserRepository();
    private final OrderRepository orderRepository=new OrderRepository();
    private final ProductRepository productRepository=new ProductRepository();
    private final OrderDetailRepository orderDetailRepository=new OrderDetailRepository();
@Override
public Order createOrder(OrderDTO orderDTO) throws Exception{
    User user=userRepository.findById(orderDTO.getUserId()) ;
    Order order=new Order();
    order.setUser(user);
    order.setOrderDate(new Date());
//    order.setStatus(OrderStatus.);
    LocalDate shippingDate = orderDTO.getShippingDate() == null
            ? LocalDate.now() : orderDTO.getShippingDate();
    if (shippingDate.isBefore(LocalDate.now())) {
        throw new DataNotFoundException("Date must be at least today !");
    }
    order.setShippingDate(shippingDate);
    order.setActive(true) ;
    order.setTotalMoney(orderDTO.getTotalMoney());
orderRepository.save(order);
    List<OrderDetail>orderDetails=new ArrayList<>();
    for(CartItemDTO cartItemDTO: orderDTO.getCartItems())
{
    OrderDetail orderDetail=new OrderDetail();
    orderDetail.setOrder(order);
    Long productId=cartItemDTO.getProductId();
    int quantity=cartItemDTO.getQuantity();
    Product product=productRepository.findProductById(productId);
    System.out.println(product);
    orderDetail.setProduct(product);
    orderDetail.setNumberOfProducts(quantity);
    orderDetail.setPrice(product.getPrice());
    orderDetails.add(orderDetail);
    orderDetailRepository.saveAll(orderDetails);
}
return order;



}

}
