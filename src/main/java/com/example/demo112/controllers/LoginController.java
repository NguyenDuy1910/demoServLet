package com.example.demo112.controllers;

import java.io.IOException;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.example.demo112.models.User;

//@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String url = "/index.html";
//        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        if(action == null)
            action="join";

        if(action.equals("join"))
            url = "/index.html";
        else if (action.equals("add")){
        String firstname =  request.getParameter("firstName");
        String lastname =  request.getParameter("lastName");
        String email =  request.getParameter("email");
        User user = new User(lastname, firstname, email);

        user.setLastName(lastname);
        user.setFirstName(firstname);
        user.setEmail(email);
        request.setAttribute("user", user);
        url = "/index.jsp";
    }

    getServletContext().getRequestDispatcher(url).forward(request, response);
}
//        boolean status = bean.validate();
//
//        if (status) {
//            RequestDispatcher rd = request
//                    .getRequestDispatcher("login-success.jsp");
//            rd.forward(request, response);
//        } else {
//            RequestDispatcher rd = request
//                    .getRequestDispatcher("login-error.jsp");
//            rd.forward(request, response);
//        }

    }

    // Rest of the code for doPost and other methods if needed



