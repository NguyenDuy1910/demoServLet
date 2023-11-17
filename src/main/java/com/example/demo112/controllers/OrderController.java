package com.example.demo112.controllers;

import com.example.demo112.dtos.OrderDTO;
import com.example.demo112.models.Order;
import com.example.demo112.service.IOrderService;
import com.example.demo112.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/order")
public class OrderController extends HttpServlet {
    private final IOrderService orderService = new OrderService();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        OrderDTO orderDTO = objectMapper.readValue(request.getInputStream(), OrderDTO.class);
        System.out.println(orderDTO);

        try {
            Order order = orderService.createOrder(orderDTO);

            // Handle successful registration
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Registration successful");

        } catch (Exception e) {
            sendBadRequestResponse(response, e.getMessage());
        }
    }

    private void sendBadRequestResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(message);
    }
}
