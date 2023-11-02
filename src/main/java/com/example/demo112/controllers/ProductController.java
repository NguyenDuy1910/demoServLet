package com.example.demo112.controllers;

import com.example.demo112.dtos.ProductDTO;
import com.example.demo112.models.Product;
import com.example.demo112.models.ProductImage;
import com.example.demo112.service.IProductService;
import com.example.demo112.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jboss.weld.context.http.Http;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/products")
public class ProductController extends HttpServlet {
    private final IProductService productService = new ProductService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        try {
            ProductDTO productDTO = objectMapper.readValue(request.getInputStream(), ProductDTO.class);
            System.out.println(productDTO);
            Product newProduct = productService.createProduct(productDTO);

            // Thực hiện các xử lý khác nếu cần
            response.getWriter().println("Product added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Failed to add product: " + e.getMessage());
        }
    }



}