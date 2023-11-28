package com.example.demo112.controllers;

import com.example.demo112.dtos.OrderDTO;
import com.example.demo112.models.Order;
import com.example.demo112.responses.*;
import com.example.demo112.service.IOrderService;
import com.example.demo112.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jboss.weld.context.http.Http;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@WebServlet(urlPatterns = "/orders/*")
public class OrderController extends HttpServlet {
    private final IOrderService orderService = new OrderService();
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        OrderDTO orderDTO = objectMapper.readValue(request.getInputStream(), OrderDTO.class);
        System.out.println(orderDTO);

        try {

            Order order = orderService.createOrder(orderDTO);
            OrderResponse orderResponse = OrderResponse.fromOrder(order);
            // Handle successful registration
            response.setStatus(HttpServletResponse.SC_OK);
            String json = objectMapper.writeValueAsString(orderResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);


        } catch (Exception e) {
            sendBadRequestResponse(response, e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.matches("/\\d+")) {
            // Extract the ID from the path variable
            String id = pathInfo.substring(1); // Remove the leading slash ("/")
            long orderId = Long.parseLong(id);

            try {
                // Fetch the Order object using the orderService
                Order existingOrder = orderService.getOrder(orderId);
                OrderResponse orderResponse = OrderResponse.fromOrder(existingOrder);

                String json = objectMapper.writeValueAsString(orderResponse);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);

                // Use the existingOrder as needed
                // ...
            } catch (Exception e) {
                // Handle any exceptions that occur during getOrder()
                e.printStackTrace();
                // ...
            }
        }
        if (pathInfo != null && pathInfo.equals("/get-orders-by-keyword")) {
            String keyword = request.getParameter("keyword");
            int page = Integer.parseInt(request.getParameter("page"));
            int limit = Integer.parseInt(request.getParameter("limit"));
            PageRequest pageRequest = PageRequest.of(
                    page - 1, limit,
                    Sort.by("id").ascending()
            );
            Page<OrderResponse> orderPage = orderService.getOrdersByKeyword(keyword, pageRequest)
                    .map(OrderResponse::fromOrder);
            int totalPages = orderPage.getTotalPages();
            List<OrderResponse> orderResponses = orderPage.getContent();
            OrderListResponse orderListResponse = new OrderListResponse();
            orderListResponse.setOrders(orderResponses);
            orderListResponse.setTotalPages(totalPages);
            String json = objectMapper.writeValueAsString(orderListResponse);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);

        }
    }


    protected void sendBadRequestResponse(HttpServletResponse response, String message) throws IOException {
        String errorMessage = message != null ? message : "Unknown error";
        int contentLength = errorMessage.length();
        response.setContentLength(contentLength);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(errorMessage);
    }

}
