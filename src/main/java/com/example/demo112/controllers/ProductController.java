package com.example.demo112.controllers;

import com.example.demo112.dtos.ProductDTO;
import com.example.demo112.models.Product;
import com.example.demo112.responses.LocalDateTimeTypeAdapter;
import com.example.demo112.responses.ProductListResponse;
import com.example.demo112.responses.ProductResponse;
import com.example.demo112.service.IProductService;
import com.example.demo112.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;


@WebServlet("/products")
public class ProductController extends HttpServlet {
    private final IProductService productService = new ProductService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try {
            ProductDTO productDTO = objectMapper.readValue(request.getInputStream(), ProductDTO.class);
            System.out.println(productDTO);
            Product product = productService.createProduct(productDTO);
            System.out.println(product);

            // Thực hiện các xử lý khác nếu cần
            response.getWriter().write("Product added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Failed to add product: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            int currentPage = Integer.parseInt(request.getParameter("page")); // Trang hiện tại
            int limit = Integer.parseInt(request.getParameter("limit")); // Số lượng mục trên mỗi trang
            int categoryId = Integer.parseInt(request.getParameter("category_id"));
            String keyword = request.getParameter("keyword");
            PageRequest pageRequest = PageRequest.of(
                    currentPage, limit,
                    Sort.by("id").ascending()
            );
            logger.info(String.format("keyword = %s, category_id = %d, page = %d, limit = %d",
                    keyword, categoryId, currentPage, limit));
            // Tính toán các thông tin phân trang
            Page<ProductResponse> productPage = productService.getAllProducts(keyword, categoryId, pageRequest);
            int totalPages = productPage.getTotalPages();
            List<ProductResponse> products = productPage.getContent();
            ProductListResponse productResponse = new ProductListResponse();
            productResponse.setProducts(products);
            productResponse.setTotalPages(totalPages);
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeTypeAdapter())
                    .create();

            String jsonString = gson.toJson(productResponse);
            response.getWriter().write(jsonString);
        } catch (NumberFormatException e) {
            // Xử lý lỗi khi không thể chuyển đổi thành số nguyên
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid request parameters");
        } catch (IOException e) {
            // Xử lý lỗi IO
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred");
        }
    }



}