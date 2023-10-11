package com.example.demo112.controllers;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;



@WebServlet("/test")
public class TestServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang web (form) cho phương thức GET
        response.setContentType("text/html");

        // Lấy PrintWriter từ đối tượng response
        PrintWriter out = response.getWriter();



     request.getRequestDispatcher("form.html").forward(request, response);
    }




}