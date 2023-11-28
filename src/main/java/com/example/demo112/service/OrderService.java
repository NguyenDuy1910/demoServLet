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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderService implements IOrderService {
    private final UserRepository userRepository=new UserRepository();
    private final OrderRepository orderRepository=new OrderRepository();
    private final ProductRepository productRepository=new ProductRepository();
    private final OrderDetailRepository orderDetailRepository=new OrderDetailRepository();
    private final ModelMapper modelMapper = new ModelMapper();
@Override
public Order createOrder(OrderDTO orderDTO) throws Exception{
    User user = userRepository.findById(orderDTO.getUserId());
    modelMapper.typeMap(OrderDTO.class, Order.class)
            .addMappings(mapper -> mapper.skip(Order::setId));
    // Cập nhật các trường của đơn hàng từ orderDTO
    Order order = new Order();
    modelMapper.map(orderDTO, order);
    order.setUser(user);
    order.setOrderDate(LocalDate.now());//lấy thời điểm hiện tại
    order.setStatus(orderDTO.getStatus());
    //Kiểm tra shipping date phải >= ngày hôm nay
    LocalDate shippingDate = orderDTO.getShippingDate() == null
            ? LocalDate.now() : orderDTO.getShippingDate();
    if (shippingDate.isBefore(LocalDate.now())) {
        throw new DataNotFoundException("Date must be at least today !");
    }
    order.setShippingDate(shippingDate);
    order.setActive(true);//đoạn này nên set sẵn trong sql
    order.setTotalMoney(orderDTO.getTotalMoney());
    orderRepository.save(order);
    List<OrderDetail>orderDetails=new ArrayList<>();
    for(CartItemDTO cartItemDTO: orderDTO.getCartItems())
{
    OrderDetail orderDetail=new OrderDetail();
    orderDetail.setOrder(order);
    Long productId=cartItemDTO.getProductId();

    int quantity=cartItemDTO.getQuantity();
    Product product = productRepository.findProductById(productId);
    orderDetail.setProduct(product);
    orderDetail.setNumberOfProducts(quantity);
    // Các trường khác của OrderDetail nếu cần
    orderDetail.setPrice(product.getPrice());

    // Thêm OrderDetail vào danh sách
    orderDetails.add(orderDetail);
}
    orderDetailRepository.saveAll(orderDetails);

    return order;



}

    @Override
    public Order getOrder(Long id) {
        Order selectedOrder = orderRepository.findById(id);
        return selectedOrder;

    }

    @Override
    public Page<Order> getOrdersByKeyword(String keyword, Pageable pageable) {
        return orderRepository.findByKeyword(keyword, pageable);
    }
}




