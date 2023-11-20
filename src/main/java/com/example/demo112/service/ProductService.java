package com.example.demo112.service;

import com.example.demo112.dtos.ProductDTO;
import com.example.demo112.exceptions.DataNotFoundException;
import com.example.demo112.models.Category;
import com.example.demo112.models.Product;
import com.example.demo112.models.ProductImage;
import com.example.demo112.repositories.CategoryRepository;
import com.example.demo112.repositories.ProductImageRepository;
import com.example.demo112.repositories.ProductRepository;
import com.example.demo112.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class ProductService implements IProductService {
    private final ProductRepository productRepository = new ProductRepository();
    private final ProductImageRepository productImageRepository = new ProductImageRepository();
    private final CategoryRepository categoryRepository = new CategoryRepository();
    @Override

    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        if (productDTO == null || productDTO.getName() == null || productDTO.getPrice() == null) {
            throw new DataNotFoundException("Missing required data for creating a product");
        }
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId());


        // Tạo đối tượng Product từ ProsetductDTO
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setCategory(existingCategory);
        product.setThumbnail(productDTO.getThumbnail());
        product.setDescription(productDTO.getDescription());

        // Lưu Product vào cơ sở dữ liệu
        return productRepository.addProduct(product);
    }
    @Override
    public ProductImage createProductImage(Long productId, String filename) throws Exception {
        Product existingProduct = productRepository.findProductById(productId);
        if (existingProduct == null) {
            throw new Exception("Product not found");
        }

        // Kiểm tra số lượng ảnh hiện tại của sản phẩm
        List<ProductImage> productImages = productImageRepository.findByProductId(productId);
        int imageSize = productImages.size();
        if (imageSize >= ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
            throw new Exception("Number of images must be <= " + ProductImage.MAXIMUM_IMAGES_PER_PRODUCT);
        }

        // Tạo đối tượng ProductImage mới
        ProductImage newProductImage = new ProductImage();
        newProductImage.setProduct(existingProduct);
        newProductImage.setImageUrl(filename);

        // Lưu ProductImage vào cơ sở dữ liệu
        return productImageRepository.save(newProductImage);
    }

    @Override
    public Page<ProductResponse> getAllProducts(String keyword,
                                                int categoryId, PageRequest pageRequest) {
        // Lấy danh sách sản phẩm theo trang (page), giới hạn (limit), và categoryId (nếu có)
        Page<Product> productsPage;
        System.out.println("hihi");
        System.out.println(categoryId);
//        List<Product> list=productRepository.findAll(1,3);
        productsPage = productRepository.searchProducts(categoryId, keyword, pageRequest);
        return productsPage.map(ProductResponse::fromProduct);
    }
}