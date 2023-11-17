package com.example.demo112.controllers;

import com.example.demo112.dtos.UserDTO;
import com.example.demo112.models.User;
import com.example.demo112.service.IUserService;
import com.example.demo112.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/register")
public class UserRegisterController extends HttpServlet {
    private final IUserService userService=new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();


    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Đặt các tiêu đề CORS
        response.setHeader("Access-Control-Allow-Origin", "*"); // Cho phép tất cả các nguồn gốc truy cập
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS"); // Cho phép các phương thức HTTP
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization"); // Cho phép các tiêu đề tùy chỉnh
        response.setHeader("Access-Control-Max-Age", "3600"); // Đặt thời gian tối đa cho bộ nhớ cache CORS

        // Gửi phản hồi
        response.setStatus(HttpServletResponse.SC_OK);
    }


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
                JsonObject jsonResponse = new JsonObject();
                jsonResponse.addProperty("message", "Registration successful");
                response.setContentType("application/json");
                response.getWriter().write(new Gson().toJson(jsonResponse));


            } catch (Exception e) {
                sendBadRequestResponse(response, e.getMessage());
            }
        }
    }
//    private void setAccessControlHeaders(HttpServletResponse resp) {
//        resp.setHeader("Access-Control-Allow-Origin", "*");
//        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");    }
    private void sendBadRequestResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(message);
    }
}