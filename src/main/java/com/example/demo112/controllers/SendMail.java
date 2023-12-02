//package com.example.demo112.controllers;
//
//import com.example.demo112.responses.MailResponse;
//
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/mail/*")
//public class SendMail extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        // Tạo một đối tượng MailResponse
//        MailResponse mailResponse = new MailResponse();
//
//        // Gửi email bằng phương thức sendSimpleMessage()
//        mailResponse.sendSimpleMessage("nguyendqduy@gmail.com");
//
//        // Cấu hình phản hồi
//      response.getWriter().write("success");
//    }}
