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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {



        String firstname = request.getParameter("firstName");
        String lastname = request.getParameter("lastName");
        String email = request.getParameter("email");
        User user = new User(lastname, firstname, email);

        request.setAttribute("user", user);
        String url = "/login.jsp";


        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/index.html"; // URL mặc định cho yêu cầu GET

        // Xử lý yêu cầu GET tại đây (nếu cần)

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}
