package com.example.demo112.controllers;

import com.example.demo112.components.JwtTokenUtils;
import com.example.demo112.dtos.UserLoginDTO;

import com.example.demo112.responses.LoginResponse;
import com.example.demo112.service.IUserService;
import com.example.demo112.service.UserService;
import com.example.demo112.utils.MessageKeys;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/login")
public class UserLoginController extends HttpServlet {
    private final IUserService userService = new UserService();
    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final JwtTokenUtils jwt=new JwtTokenUtils();

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
        response.setContentType("application/json"); // Thiết lập kiểu nội dung phản hồi là JSON

        UserLoginDTO userLoginDTO = objectMapper.readValue(request.getInputStream(), UserLoginDTO.class);
                try {
        String token = userService.login(
                userLoginDTO.getPhoneNumber(),
                userLoginDTO.getPassword(),
                userLoginDTO.getRoleId() == null ? 1 : userLoginDTO.getRoleId()
        );
//        lấy số điện thoại từ token
//        System.out.println(jwt.extractPhoneNumber(token));

                    LoginResponse loginResponse = new LoginResponse(MessageKeys.LOGIN_SUCCESSFULLY, token);
        response.setStatus(HttpServletResponse.SC_OK);

                    response.getWriter().write(new Gson().toJson(loginResponse));


                } catch (Exception e) {
                    response.setContentType("text/plain"); // Thiết lập kiểu nội dung phản hồi là văn bản thuần

                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().write(MessageKeys.LOGIN_FAILED);// Thiết lập mã trạng thái phản hồi là 400 Bad Request
                }
}
//    private void sendBadRequestResponse(HttpServletResponse response, String message) throws IOException {
//        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//        response.getWriter().write(message);
//    }
}
