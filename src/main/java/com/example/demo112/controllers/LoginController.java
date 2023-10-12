package com.example.demo112.controllers;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo112.models.User;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String url = "/form.html";

        String action = request.getParameter("action");

        if (action == null)
            action = "join";

        if (action.equals("join"))
            url = "/form.html";

        else if (action.equals("add")) {
            String firstname = request.getParameter("firstName");
            String lastname = request.getParameter("lastName");
            String email = request.getParameter("email");
            User user = new User(lastname, firstname, email);

            request.setAttribute("user", user);
            url = "/WEB-INF/index.jsp";
        }

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = "/form.html";

        String action = request.getParameter("action");

        if (action == null)
            action = "join";

        if (action.equals("join"))
            url = "/form.html";

        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
}
