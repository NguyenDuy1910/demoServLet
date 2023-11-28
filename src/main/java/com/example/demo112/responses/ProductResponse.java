package com.example.demo112.responses;

import com.example.demo112.models.Product;
import com.example.demo112.models.ProductImage;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ProductResponse extends BaseResponse {
    private Long id;
    private String name;
    private Float price;
    private String thumbnail;
    private String description;
    @JsonProperty("product_images")
    private List<ProductImage> productImages = new ArrayList<>();

    @JsonProperty("category_id")
    private Long categoryId;

    public ProductResponse(Long id, String name, Float price, String thumbnail, String description, List<ProductImage> productImages, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
        this.description = description;
        this.productImages = productImages;
        this.categoryId = categoryId;
    }

    public ProductResponse() {
    }

    public static ProductResponse fromProduct(Product product) {
        ProductResponse productResponse = new ProductResponse(product.getId(), product.getName(),
                product.getPrice(), product.getThumbnail(), product.getDescription(),
                product.getProductImages(), product.getCategory().getId());
        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());
        return productResponse;


    }

    public static List<ProductResponse> fromProducts(List<Product> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            ProductResponse productResponse = new ProductResponse(product.getId(), product.getName(),
                    product.getPrice(), product.getThumbnail(), product.getDescription(),
                    product.getProductImages(), product.getCategory().getId());
            productResponse.setCreatedAt(product.getCreatedAt());
            productResponse.setUpdatedAt(product.getUpdatedAt());
            productResponses.add(productResponse);
        }
        return productResponses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }
}
