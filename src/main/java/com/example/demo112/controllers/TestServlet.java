package com.example.demo112.controllers;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;



@WebServlet("/test")
public class TestServlet extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Hiển thị trang web (form) cho phương thức GET
        response.setContentType("text/html");

        // Lấy PrintWriter từ đối tượng response
        PrintWriter out = response.getWriter();

        // Đoạn văn bạn muốn hiển thị
        String paragraph = "Đây là một ví dụ về việc hiển thị một câu văn bằng Servlet.";

        // Ghi đoạn văn vào phản hồi
        out.println(paragraph);

        // Đảm bảo rằng bạn đã đóng PrintWriter
        out.close();

//        request.getRequestDispatcher("form.html").forward(request, response);
    }




}