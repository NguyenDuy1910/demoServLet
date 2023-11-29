package com.example.demo112.controllers;

import com.example.demo112.dtos.CategoryDTO;
import com.example.demo112.dtos.ProductDTO;
import com.example.demo112.models.Category;
import com.example.demo112.responses.CategoryResponse;
import com.example.demo112.service.CategoryService;
import com.example.demo112.service.ICategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.jboss.weld.context.http.Http;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/categories/*")
public class CategoryController extends HttpServlet {
    private final ICategoryService categoryService = new CategoryService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt các tiêu đề CORS
        response.setHeader("Access-Control-Allow-Origin", "*"); // Cho phép tất cả các nguồn gốc truy cập
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // Cho phép các phương thức HTTP
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); // Cho phép các tiêu đề tùy chỉnh
        response.setHeader("Access-Control-Max-Age", "3600"); // Đặt thời gian tối đa cho bộ nhớ cache CORS

        // Gửi phản hồi
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            CategoryDTO categoryDTO = objectMapper.readValue(request.getInputStream(), CategoryDTO.class);
            CategoryResponse categoryResponse = new CategoryResponse();
            Category category = categoryService.createCategory(categoryDTO);
            categoryResponse.setCategory(category);
            Gson gson = new Gson();
            response.setCharacterEncoding("utf-8");

            response.getWriter().write(gson.toJson(category));

        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<Category> categorys = categoryService.getAllCategories();
            Gson gson = new Gson();
            response.setCharacterEncoding("utf-8");

            response.getWriter().write(gson.toJson(categorys));

        }
    }
}
