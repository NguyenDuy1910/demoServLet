//package com.example.demo112.controllers;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//@WebServlet("/testcores")
//
//public class testcore extends HttpServlet {
//
//        @Override
//        protected void doGet(HttpServletRequest req, HttpServletResponse resp)
//                throws ServletException, IOException {
//            setAccessControlHeaders(resp);
//            PrintWriter writer = resp.getWriter();
//            writer.write("test response from myServlet");
//        }
//
//        //for Preflight
//        @Override
//        protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
//                throws ServletException, IOException {
//            setAccessControlHeaders(resp);
//            resp.setStatus(HttpServletResponse.SC_OK);
//        }
//
//        private void setAccessControlHeaders(HttpServletResponse resp) {
//            resp.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5500");
//            resp.setHeader("Access-Control-Allow-Methods", "GET");
//        }
//    }
//
