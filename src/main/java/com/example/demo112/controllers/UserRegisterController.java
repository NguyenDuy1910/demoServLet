package com.example.demo112.controllers;

import com.example.demo112.dtos.UserDTO;
import com.example.demo112.models.User;
import com.example.demo112.service.IUserService;
import com.example.demo112.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class UserRegisterController extends HttpServlet {
    private final IUserService userService=new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();





    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // Handle registration
        UserDTO userDTO = objectMapper.readValue(request.getInputStream(), UserDTO.class);
        System.out.println(userDTO);

        if (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
            sendBadRequestResponse(response, "Mật khẩu không đúng");
        } else {
            try {
                User user = userService.createUser(userDTO);
                // Handle successful registration
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Registration successful");
            } catch (Exception e) {
                sendBadRequestResponse(response, e.getMessage());
            }
        }
    }

    private void sendBadRequestResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(message);
    }
}