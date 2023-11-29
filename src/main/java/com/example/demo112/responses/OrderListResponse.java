package com.example.demo112.responses;

import java.util.List;

public class OrderListResponse {
    private List<OrderResponse> orders;
    private int totalPages;

    public List<OrderResponse> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderResponse> orders) {
        this.orders = orders;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
