//package com.example.demo112.controllers;
//
//import com.example.demo112.models.ProductImage;
//import com.example.demo112.service.IProductService;
//import com.example.demo112.service.ProductService;
//
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@WebServlet("products/images")
//@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
//        maxFileSize = 1024 * 1024 * 10, // 10MB
//        maxRequestSize = 1024 * 1024 * 50) // 50MB
//public class ProductImageController extends HttpServlet {
//
//    private final IProductService productService = new ProductService();
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        try {
//            String productIdString = request.getPathInfo().substring(1); // Extract the id from the path
//            Long productId = Long.parseLong(productIdString);
//
//            List<Part> files = request.getParts().stream()
//                    .filter(part -> "files".equals(part.getName()))
//                    .collect(Collectors.toList());
//
//            if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
//                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//                return;
//            }
//
//            List<ProductImage> productImages = new ArrayList<>();
//            for (Part filePart : files) {
//                if (filePart.getSize() == 0) {
//                    continue;
//                }
//
//                // Kiểm tra kích thước file và định dạng
//                if (filePart.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
//                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                    return;
//                }
//
//                String contentType = filePart.getContentType();
//                if(contentType == null || !contentType.startsWith("image/")) {
//                    response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
//                    return;
//                }
//
//                // Lưu file và cập nhật thumbnail trong DTO
//                String filename = storeFile(filePart);
//                System.out.println(filename);
//                // Lưu vào đối tượng product trong DB
//                ProductImage productImage = productService.createProductImage(productId, filename);
//                productImages.add(productImage);
//            }
//
//            response.setStatus(HttpServletResponse.SC_OK);
//            response.getWriter().write(productImages.toString());
//        } catch (Exception e) {
//            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            response.getWriter().write(e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    private String storeFile(Part filePart) throws IOException {
//        if (!isImageFile(filePart) || filePart.getSubmittedFileName() == null) {
//            throw new IOException("Invalid image format");
//        }
//        String filename = Objects.requireNonNull(filePart.getSubmittedFileName());
//        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
//        java.nio.file.Path uploadDir = Paths.get("/home/quocduy/Documents/JspServLetUTE/demoServLet/uploads");
//
//        if (!Files.exists(uploadDir)) {
//            Files.createDirectories(uploadDir);
//        }
//        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
//        Files.copy(filePart.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
//        return uniqueFilename;
//    }
//
//    private boolean isImageFile(Part filePart) {
//        String contentType = filePart.getContentType();
//        return contentType != null && contentType.startsWith("image/");
//    }
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse) throws IOException
//    {
//
//    }
//
//}