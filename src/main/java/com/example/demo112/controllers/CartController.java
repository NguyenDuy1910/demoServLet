package com.example.demo112.controllers;
import java.io.IOException;
import javax.servlet.annotation.MultipartConfig;
import com.google.gson.Gson;
import com.example.demo112.service.InsertService;
import com.example.demo112.service.impl.InsertServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.io.BufferedReader;

@WebServlet(urlPatterns = "/view")
@MultipartConfig
public class CartController extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null)
            action = "join";
        if (action.equals("join")) {
            req.getRequestDispatcher("cart/viewproduct.jsp").forward(req, resp);
        } else if (action.equals("cart")) {
            req.getRequestDispatcher("cart/viewcart.jsp").forward(req, resp);
        }
    }



        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String id_product = null;
            String u_product = null;
            String prices = null;

            for (Part part : req.getParts()) {
                String fieldName = part.getName();
                if (fieldName.equals("id_product")) {
                    id_product = req.getParameter(fieldName);
                } else if (fieldName.equals("u_product")) {
                    u_product = req.getParameter(fieldName);
                } else if (fieldName.equals("prices")) {
                    prices = req.getParameter(fieldName);
                }
            }

            System.out.println("ID Product: " + id_product);
            System.out.println("User Product: " + u_product);
            System.out.println("Prices: " + prices);

            InsertService service = new InsertServiceImpl();
            boolean insertion = service.insertion(id_product, u_product, prices);

            ResponeData responseData = new ResponeData();
            if (insertion) {
                responseData.setStatus("success");
                responseData.setMessage("Thêm giỏ hàng thành công.");
            } else {
                responseData.setStatus("error");
                responseData.setMessage("Không thể thêm sản phẩm vào giỏ hàng.");
            }

            Gson gson = new Gson();
            String jsonResponse = gson.toJson(responseData);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonResponse);
        }
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        InsertService service = new InsertServiceImpl();
        String id_product = request.getParameter("id");
        boolean delete = service.delete(id_product);

        if (delete) {
            // Xóa thành công
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Delete not success");
        } else {
            // Xóa không thành công
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Delete not success");
        }
    }
    }