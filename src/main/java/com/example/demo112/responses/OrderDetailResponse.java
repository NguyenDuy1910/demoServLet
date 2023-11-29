package com.example.demo112.responses;

import com.example.demo112.models.OrderDetail;

public class OrderDetailResponse {
    private Long id;
    private Long orderId;
    private Long productId;
    private Float price;
    private int numberOfProducts;
    private Float totalMoney;
    private String color;

    public OrderDetailResponse() {
    }

    public OrderDetailResponse(Long id, Long orderId, Long productId, Float price, int numberOfProducts, Float totalMoney, String color) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.price = price;
        this.numberOfProducts = numberOfProducts;
        this.totalMoney = totalMoney;
        this.color = color;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getNumberOfProducts() {
        return numberOfProducts;
    }

    public void setNumberOfProducts(int numberOfProducts) {
        this.numberOfProducts = numberOfProducts;
    }

    public Float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(Float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {
        return new OrderDetailResponse(
                orderDetail.getId(),
                orderDetail.getOrder().getId(),
                orderDetail.getProduct().getId(),
                orderDetail.getPrice(),
                orderDetail.getNumberOfProducts(),
                orderDetail.getTotalMoney(),
                orderDetail.getColor()
        );
    }
}
