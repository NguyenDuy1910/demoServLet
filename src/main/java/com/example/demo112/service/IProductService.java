package com.example.demo112.service;

import com.example.demo112.dtos.ProductDTO;
import com.example.demo112.exceptions.DataNotFoundException;
import com.example.demo112.models.Product;
import com.example.demo112.models.ProductImage;
import com.example.demo112.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;

    Product getProductById(Long id) throws IOException;
Page<ProductResponse> getAllProducts(String keyword, int categoryId, PageRequest pageRequest);
//Product updateProduct(long id, ProductDTO productDTO) throws Exception;
//    void deleteProduct(long id);
//    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            String filename) throws Exception;

    List<Product> findProductsByIds(List<Long> productIds);

}
