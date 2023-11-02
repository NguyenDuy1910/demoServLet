package com.example.demo112.service;

import com.example.demo112.dtos.ProductDTO;
import com.example.demo112.dtos.ProductImageDTO;
import com.example.demo112.exceptions.DataNotFoundException;
import com.example.demo112.models.Product;
import com.example.demo112.models.ProductImage;

import java.util.List;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws DataNotFoundException;
//    Product getProductById(long id ) throws Exception;
//    public Page<ProductResponse> getAllProducts(String keyword,Long categoryId,PageRequest pageRequest);
//Product updateProduct(long id, ProductDTO productDTO) throws Exception;
//    void deleteProduct(long id);
//    boolean existsByName(String name);
    ProductImage createProductImage(
            Long productId,
            String filename) throws Exception;

//    List<Product> findProductsById(List<Long> productIds);

}
