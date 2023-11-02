//package com.example.demo112;
//import com.example.demo112.controllers.UserController;
//import org.eclipse.jetty.server.Server;
//import org.eclipse.jetty.servlet.ServletContextHandler;
//import org.eclipse.jetty.servlet.ServletHolder;
//
//
//public class JettyServer {
//    public static void main(String[] args) throws Exception {
//        Server server = new Server(8080);
//
//        ServletContextHandler contextHandler = new ServletContextHandler();
//        contextHandler.setContextPath("/");
//
//        ServletHolder servletHolder = new ServletHolder(UserController.class);
//        contextHandler.addServlet(servletHolder, "/user");
//
//        server.setHandler(contextHandler);
//        server.start();
//        server.join();
//    }
//}