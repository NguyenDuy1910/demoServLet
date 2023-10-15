package com.example.demo112.dao;
import com.example.demo112.models.CartInsert;
import java.util.List;
public interface InsertDao {
//    int checkIdProduct( String id_product);

    void add(CartInsert cart);
    void delete(String id_product);
    List<CartInsert> getAll();

}
