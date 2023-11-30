package com.example.demo112.controllers;

import com.example.demo112.dtos.ProductDTO;
import com.example.demo112.models.Product;
import com.example.demo112.models.ProductImage;
//import com.example.demo112.responses.HibernateProxyTypeAdapter;
import com.example.demo112.responses.*;
import com.example.demo112.service.IProductService;
import com.example.demo112.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.activation.MimetypesFileTypeMap;
import org.hibernate.proxy.HibernateProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@WebServlet("/products/*")

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50) // 50MB
public class ProductController extends HttpServlet {
    private final IProductService productService = new ProductService();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
//    private final IProductService productService = new ProductService();


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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());



        try {
            ProductDTO productDTO = objectMapper.readValue(request.getInputStream(), ProductDTO.class);
            Product newproduct = productService.createProduct(productDTO);
            response.setStatus(HttpServletResponse.SC_OK);
            String json = objectMapper.writeValueAsString(newproduct);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            // Thực hiện các xử lý khác nếu cần
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Failed to add product: " + e.getMessage());
        }
        } else if (pathInfo.startsWith("/uploads/")) {

            try {
                String productIdString = pathInfo.substring("/uploads/".length()); // Extract the id from the path
                Long productId = Long.parseLong(productIdString);

                List<Part> files = request.getParts().stream()
                        .filter(part -> "files".equals(part.getName()))
                        .collect(Collectors.toList());

                if (files.size() > ProductImage.MAXIMUM_IMAGES_PER_PRODUCT) {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }

                List<ProductImage> productImages = new ArrayList<>();
                String thumbnailUrl = null; // Biến lưu trữ URL thumbnail

                for (Part filePart : files) {
                    if (filePart.getSize() == 0) {
                        continue;
                    }

                    // Kiểm tra kích thước file và định dạng
                    if (filePart.getSize() > 10 * 1024 * 1024) { // Kích thước > 10MB
                        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                        return;
                    }

                    String contentType = filePart.getContentType();
                    if (contentType == null || !contentType.startsWith("image/")) {
                        response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
                        return;
                    }

                    // Lưu file và cập nhật thumbnail trong DTO
                    String filename = storeFile(filePart);
                    if (thumbnailUrl == null) {
                        thumbnailUrl = filename;
                    }
                    System.out.println(filename);

                    // Lưu vào đối tượng product trong DB
                    ProductImage productImage = productService.createProductImage(productId, filename);
                    productImages.add(productImage);
                }
                Product upload = productService.updateProductThumbnail(productId, thumbnailUrl);
                response.setStatus(HttpServletResponse.SC_OK);
                String json = objectMapper.writeValueAsString(upload);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);



            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(e.getMessage());
                e.printStackTrace();
            }


        }

    }

    private String storeFile(Part filePart) throws IOException {
        if (!isImageFile(filePart) || filePart.getSubmittedFileName() == null) {
            throw new IOException("Invalid image format");
        }
        String filename = Objects.requireNonNull(filePart.getSubmittedFileName());
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        java.nio.file.Path uploadDir = Paths.get("/home/quocduy/Documents/JspServLetUTE/demoServLet/uploads");

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        Files.copy(filePart.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    private boolean isImageFile(Part filePart) {
        String contentType = filePart.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            try {
                int currentPage = Integer.parseInt(request.getParameter("page")); // Trang hiện tại
                int limit = Integer.parseInt(request.getParameter("limit")); // Số lượng mục trên mỗi trang
                int categoryId = Integer.parseInt(request.getParameter("category_id"));
                String keyword = request.getParameter("keyword");
                PageRequest pageRequest = PageRequest.of(
                        currentPage, limit,
                        Sort.by("id").ascending()
                );
                logger.info(String.format("keyword = %s, category_id = %d, page = %d, limit = %d",
                        keyword, categoryId, currentPage, limit));
                // Tính toán các thông tin phân trang
                Page<ProductResponse> productPage = productService.getAllProducts(keyword, categoryId, pageRequest);
                int totalPages = productPage.getTotalPages();
                List<ProductResponse> products = productPage.getContent();
                ProductListResponse productResponse = new ProductListResponse();
                productResponse.setProducts(products);
                productResponse.setTotalPages(totalPages);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapterFactory(new ProductResponseTypeAdapterFactory());
                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter());
                Gson gson = gsonBuilder.create();
                String jsonString = gson.toJson(productResponse);
                response.setStatus(200);
                response.getWriter().write(jsonString);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid parameters");
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("An error occurred");
                e.printStackTrace();
            }

        } else if (pathInfo.startsWith("/images/")) {
            String productImagesString = pathInfo.substring("/images/".length()); // Extract the image name from the path
            String imageName = String.format(productImagesString);
            java.nio.file.Path imagePath = Paths.get("/home/quocduy/Documents/JspServLetUTE/demoServLet/uploads/" + imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            try {
                if (resource.exists()) {
                    String contentType = getServletContext().getMimeType(resource.toString());
                    response.setContentType(contentType);

                    Files.copy(imagePath, response.getOutputStream());
                } else {
                    response.getWriter().write("no");


                }
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (pathInfo.matches("/\\d+")) {
            String idString = pathInfo.substring(1); // Bỏ đi ký tự '/' ở đầu
            try {
                long productId = Long.parseLong(idString);
                Product existingProduct = productService.getProductById(productId);
//            ProductResponse products=new ProductResponse(fromProduct);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapterFactory(new ProductResponseTypeAdapterFactory());
                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter());
                Gson gson = gsonBuilder.create();
                String jsonString = gson.toJson(ProductResponse.fromProduct(existingProduct));
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(jsonString);

                // Thực hiện các xử lý khác với existingProduct...
            } catch (IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else if (pathInfo.startsWith("/by-ids")) {
            String ids = request.getParameter("ids"); // Get the value of the "ids" parameter from the request
            try {
                List<Long> productIds = Arrays.stream(ids.split(","))
                        .map(Long::parseLong)
                        .collect(Collectors.toList());
                List<Product> products = productService.findProductsByIds(productIds);

                // Set the response content type and encoding
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapterFactory(new ProductResponseTypeAdapterFactory());
                gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter());
                Gson gson = gsonBuilder.create();
                String jsonString = gson.toJson(ProductResponse.fromProducts(products));
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                response.getWriter().write(jsonString);
            } catch (Exception e) {
                // Handle any exceptions that occur
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            }


        }


    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.matches("/\\d+")) {
            // Extract the ID from the path variable
            String id = pathInfo.substring(1); // Remove the leading slash ("/")
            long orderId = Long.parseLong(id);
            try {
                ProductDTO productDTO = objectMapper.readValue(request.getInputStream(), ProductDTO.class);


                Product updatedProduct = productService.updateProduct(orderId, productDTO);
                response.setStatus(HttpServletResponse.SC_OK);
                String json = objectMapper.writeValueAsString(updatedProduct);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (Exception e) {
                // Handle any exceptions that occur
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.matches("/\\d+")) {
            // Extract the ID from the path variable

            try {
                String id = pathInfo.substring(1); // Remove the leading slash ("/")
                long productId = Long.parseLong(id);

                productService.deleteProduct(productId);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("message", String.format("Product with id = %d deleted successfully", productId));

// Create an instance of Gson
                Gson gson = new Gson();

// Convert the JsonObject to JSON format
                String json = gson.toJson(jsonObject);

// Write the JSON string to the response
                response.getWriter().write(json);
            } catch (Exception e) {
                // Handle any exceptions that occur
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
            }

        }

    }
}




