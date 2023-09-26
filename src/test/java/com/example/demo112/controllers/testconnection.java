package com.example.demo112.controllers;
import com.example.demo112.models.DatabaseConnection;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test")
public class testconnection extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/index.html";



//        if (DatabaseConnection.testConnection())
            getServletContext().getRequestDispatcher(url).forward(request, response);



    }
}
