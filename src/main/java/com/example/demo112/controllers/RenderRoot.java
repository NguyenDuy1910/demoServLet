package com.example.demo112.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("")

public class RenderRoot extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý yêu cầu GET tại đây
        // Ví dụ: Hiển thị form đăng nhập
        String url = "/WEB-INF/views/root.html";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }}