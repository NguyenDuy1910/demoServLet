package com.example.demo112.controllers;

import com.example.demo112.components.JwtTokenUtils;
import com.example.demo112.dtos.UserLoginDTO;
import com.example.demo112.models.User;
import com.example.demo112.responses.LocalDateAdapter;
import com.example.demo112.responses.LoginResponse;
import com.example.demo112.responses.ProductResponseTypeAdapterFactory;
import com.example.demo112.responses.UserResponse;
import com.example.demo112.service.IUserService;
import com.example.demo112.service.UserService;
import com.example.demo112.utils.MessageKeys;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/users/*")
public class UserLoginController extends HttpServlet {
    private final IUserService userService = new UserService();
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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().println("hello");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        if ("/login".equals(pathInfo)) {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");

            UserLoginDTO userLoginDTO = objectMapper.readValue(request.getInputStream(), UserLoginDTO.class);
            try {
                String token = userService.login(
                        userLoginDTO.getPhoneNumber(),
                        userLoginDTO.getPassword(),
                        userLoginDTO.getRoleId() == null ? 1 : userLoginDTO.getRoleId()
                );

                LoginResponse loginResponse = new LoginResponse(MessageKeys.LOGIN_SUCCESSFULLY, token);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(new Gson().toJson(loginResponse));
            } catch (Exception e) {
                response.setContentType("text/plain");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(MessageKeys.LOGIN_FAILED);
            }
        } else if ("/details".equals(pathInfo)) {
            String authorizationHeader = request.getHeader("Authorization");
            try {
                String extractedToken = authorizationHeader.substring(7);
                User user = userService.getUserDetailsFromToken(extractedToken);
                UserResponse userResponse = UserResponse.fromUser(user);

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapterFactory(new ProductResponseTypeAdapterFactory());
                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter());
                Gson gson = gsonBuilder.create();
                String jsonString = gson.toJson(userResponse);
                System.out.println(userResponse);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(jsonString);
            } catch (Exception e) {
                response.setContentType("text/plain");
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(MessageKeys.LOGIN_FAILED);
            }
        }
    }
}