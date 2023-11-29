package com.example.demo112.responses;

import com.example.demo112.models.ProductImage;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class ProductResponseTypeAdapter extends TypeAdapter<ProductResponse> {

    @Override
    public void write(JsonWriter out, ProductResponse productResponse) throws IOException {
        out.beginObject();
        out.name("id").value(productResponse.getId());
        out.name("name").value(productResponse.getName());
        out.name("price").value(productResponse.getPrice());
        out.name("thumbnail").value(productResponse.getThumbnail());
        out.name("description").value(productResponse.getDescription());

        out.name("product_images");
        out.beginArray();
        for (ProductImage image : productResponse.getProductImages()) {
            out.beginObject();
            out.name("image_url").value(image.getImageUrl());
            out.endObject();
        }
        out.endArray();

//        out.name("category_id").value(productResponse.getCategoryId());

        // Ghi các trường khác của ProductResponse vào đây (nếu có)
        out.endObject();
    }

    @Override
    public ProductResponse read(JsonReader in) throws IOException {
        // Không cần thiết nếu bạn chỉ muốn chuyển đổi từ JSON thành đối tượng Java
        return null;
    }
}