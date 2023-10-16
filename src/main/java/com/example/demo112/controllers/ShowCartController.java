package com.example.demo112.controllers;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import com.example.demo112.service.InsertService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import com.example.demo112.service.impl.InsertServiceImpl;
import javax.servlet.annotation.WebServlet;

import com.example.demo112.models.CartInsert;

@WebServlet(urlPatterns = "/show")
public class ShowCartController extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.getRequestDispatcher("cart/viewcart.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  IOException
    {

        InsertService service=new InsertServiceImpl();
        List<CartInsert> cartlist=service.getAll();
        System.out.println(cartlist);
        req.setAttribute("cartlist",cartlist);
        Gson gson = new Gson();
        String jsonData = gson.toJson(cartlist);
        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(jsonData);

    }



//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");



    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InsertService service = new InsertServiceImpl();
        String id_product = request.getParameter("id");
        boolean delete = service.delete(id_product);

        if (delete) {
            // Xóa thành công
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Delete  success");
        } else {
            // Xóa không thành công
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Delete not success");
        }
    }


}
