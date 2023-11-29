package com.example.demo112.controllers;

import com.example.demo112.models.Role;
import com.example.demo112.service.IRoleService;
import com.example.demo112.service.RoleService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/roles")
public class RoleController extends HttpServlet {
    private final IRoleService roleService = new RoleService();

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            List<Role> roles = roleService.getAllRoles();
            response.getWriter().write(new Gson().toJson(roles));
        }
    }
}
