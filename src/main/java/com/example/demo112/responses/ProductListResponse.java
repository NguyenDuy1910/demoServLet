package com.example.demo112.responses;

import java.util.List;

public class ProductListResponse {
    private List<ProductResponse> products;
    private int totalPages;

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ProductListResponse() {
    }
}
