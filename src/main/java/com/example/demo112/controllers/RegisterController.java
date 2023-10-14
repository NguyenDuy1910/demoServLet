package com.example.demo112.controllers;
import javax.servlet.ServletException;
import java.io.Console;
import java.io.IOException;
import java.util.List;
//import com.example.demo112.configurations.WebSecurityConfig;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.demo112.models.User;
import com.example.demo112.service.UserService;
import com.example.demo112.service.impl.UserServiceImpl;

@WebServlet(urlPatterns = "/register")

public class RegisterController extends HttpServlet
{

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        HttpSession session = req.getSession(false);
//        if (session != null && session.getAttribute("username") != null) {
//            resp.sendRedirect(req.getContextPath() + "/admin");
//            return;
//        }
//        // Check cookie
//        Cookie[] cookies = req.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("username")) {
//                    session = req.getSession(true);
//                    session.setAttribute("username", cookie.getValue());
//                    resp.sendRedirect(req.getContextPath() + "/admin");
//                    return;
//                }
//            }
//        }
//
//        req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
//    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
//         WebSecurityConfig.setAccessControlHeaders(resp);
        System.out.println(resp);
        System.out.println(resp.getHeader("Access-Control-Allow-Origin"));

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        System.out.println(username);
        System.out.println(email);
        System.out.println(password);

        UserService service = new UserServiceImpl();
        String alertMsg = "";


        if (service.checkExistEmail(email)) {
            alertMsg = "Email already exist!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("form.html").forward(req, resp);
            return;
        }
        if (service.checkExistUsername(username)) {
            alertMsg = "Username already exits!";
            req.setAttribute("alert", alertMsg);
            req.getRequestDispatcher("form.html").forward(req, resp);
            return;
        }

            boolean isSuccess = service.register(username, email, password);
            System.out.println(isSuccess);

            if (isSuccess) {
                List<User> userList = service.getAll();

                // Đặt danh sách người dùng trong thuộc tính "userList" của request
                req.setAttribute("userList", userList);
                System.out.println(userList);
                // Chuyển hướng đến trang JSP để hiển thị danh sách người dùng
                req.getRequestDispatcher("userlist.jsp").forward(req, resp);//
            } else {
                // Xử lý khi đăng kí không thành công (ví dụ: hiển thị thông báo lỗi)
                alertMsg = "Registration failed!";
                req.setAttribute("alert", alertMsg);
                req.getRequestDispatcher("form.html").forward(req, resp);
            }


//
//        if (isSuccess) {
//            SendMail sm = new SendMail();
//            sm.sendMail(email, "UNIFY", "Welcome to UNIFY. Please Login to use service. Thanks !");
//            req.setAttribute("alert", alertMsg);
//            resp.sendRedirect(req.getContextPath() + "/login");
//        } else {
//            alertMsg = "System error!";
//            req.setAttribute("alert", alertMsg);
//            req.getRequestDispatcher(Constant.Path.REGISTER).forward(req, resp);
//        }
    }


}


