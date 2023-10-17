//package com.example.demo112.controllers;
//
//import java.io.IOException;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.annotation.WebServlet;
//
//@WebServlet(urlPatterns = "/")
//
//public class DefaultController extends HttpServlet {
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Xử lý yêu cầu GET
//        // Gửi đáp ứng trang hiển thị mặc định
//
//        // Ví dụ: Chuyển hướng yêu cầu đến trang hiển thị mặc định
//        request.getRequestDispatcher("index.html").forward(request, response);
//    }
//}