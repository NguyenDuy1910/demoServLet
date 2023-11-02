package com.example.demo112.controllers;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

@WebServlet("/product/*")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class testPathID extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads"; // Directory to store uploaded files

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Collection<Part> parts = request.getParts(); // Lấy danh sách các Part trong yêu cầu

            for (Part part : parts) {
                String fieldName = part.getName(); // Lấy tên trường (field) trong form-data
                String fieldValue = readPartValue(part); // Đọc giá trị của trường (field) từ Part

                // Xử lý giá trị của trường (field) ở đây...
                System.out.println("Field: " + fieldName);
                System.out.println("Value: " + fieldValue);
            }

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Data received successfully");
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
            e.printStackTrace();
        }
    }

    // Phương thức đọc giá trị của một Part
    private String readPartValue(Part part) throws IOException {
        StringBuilder value = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                value.append(line);
            }
        }
        return value.toString();
    }}