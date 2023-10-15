package com.example.demo112.service;
import java.util.List;
import com.example.demo112.models.CartInsert;

public interface InsertService {
     void insert(CartInsert cart);
     boolean insertion(String id_product,String u_product,String prices);
     boolean delete(String id_product);
//     int checkIdProduct(String id_product);
     List<CartInsert> getAll();
}
