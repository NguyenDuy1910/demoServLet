package com.example.demo112.controllers;

import com.example.demo112.dtos.UserLoginDTO;

import com.example.demo112.service.IUserService;
import com.example.demo112.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/login")
public class UserLoginController extends HttpServlet {
    private final IUserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected  void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        response.getWriter().println("hello");
        /////
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        UserLoginDTO userLoginDTO = objectMapper.readValue(request.getInputStream(), UserLoginDTO.class);
                try {
        String token = userService.login(
                userLoginDTO.getPhoneNumber(),
                userLoginDTO.getPassword(),
                userLoginDTO.getRoleId() == null ? 1 : userLoginDTO.getRoleId()
        );
        // Handle successful login
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write("Login successful");
    } catch (Exception e) {
        sendBadRequestResponse(response, e.getMessage());
    }
}
    private void sendBadRequestResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(message);
    }
}
