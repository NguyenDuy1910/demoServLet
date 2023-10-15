package com.example.demo112.controllers;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;
import com.example.demo112.service.InsertService;

import com.example.demo112.service.impl.InsertServiceImpl;

import com.example.demo112.models.CartInsert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns = "/show")
public class ShowCartController extends HttpServlet
{
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



//        resp.setContentType("application/json");
//        resp.setCharacterEncoding("UTF-8");


    }


}
