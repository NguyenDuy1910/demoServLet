package com.example.demo112.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.demo112.models.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Xử lý yêu cầu GET tại đây
        // Ví dụ: Hiển thị form đăng nhập
        String url = "/WEB-INF/views/index.html";
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");
        String email = request.getParameter("email");
        User user = new User(lastname, firstname, email);

        request.setAttribute("user", user);
        String url = "/WEB-INF/views/login.jsp";

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    // Rest of the code for doPost and other methods if needed
}


