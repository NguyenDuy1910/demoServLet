package com.example.demo112.controllers;

import com.example.demo112.dtos.OrderDetailDTO;
import com.example.demo112.models.OrderDetail;
import com.example.demo112.responses.OrderDetailResponse;
import com.example.demo112.service.IOrderDetailService;
import com.example.demo112.service.OrderDetailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/order_details/*")
public class OrderDetailController extends HttpServlet {
    private final IOrderDetailService orderDetailService = new OrderDetailService();
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
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                OrderDetailDTO orderDetailDTO = objectMapper.readValue(request.getInputStream(), OrderDetailDTO.class);
                OrderDetail newOrderDetail = orderDetailService.createOrderDetail(orderDetailDTO);
                OrderDetailResponse orderDetailResponse = OrderDetailResponse.fromOrderDetail(newOrderDetail);
                String json = objectMapper.writeValueAsString(orderDetailResponse);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("An error occurred");
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Lấy chi tiết đơn hàng của một order nào đó

        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.matches("/order/\\d+")) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                String orderIdParam = pathInfo.substring(pathInfo.lastIndexOf('/') + 1);
                Long orderId = Long.parseLong(orderIdParam);

                List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);

                List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                        .map(OrderDetailResponse::fromOrderDetail)
                        .collect(Collectors.toList());
                String json = objectMapper.writeValueAsString(orderDetailResponses);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("An error occurred");
                e.printStackTrace();
            }
        }
    }
}





