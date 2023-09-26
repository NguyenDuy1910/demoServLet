package com.example.demo112.controllers;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo112.models.User;

@WebServlet("/test")
public class TestServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang web (form) cho phương thức GET
        request.getRequestDispatcher("index.html").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Xử lý dữ liệu từ biểu mẫu sau khi người dùng nhấn nút "Submit" (phương thức POST)
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        // Thực hiện xử lý dữ liệu (ví dụ: lưu vào cơ sở dữ liệu)

        // Hiển thị dòng chữ "TestServlet Post"
        response.getWriter().write("TestServlet Post");

        // Bạn có thể thêm mã HTML để định dạng hiển thị, ví dụ:
        // out.println("<h1>TestServlet Post</h1>");
    }
}