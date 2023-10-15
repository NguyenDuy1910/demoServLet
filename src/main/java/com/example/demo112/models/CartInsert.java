package com.example.demo112.models;

import java.io.Serializable;

public class CartInsert implements Serializable {
    private String id_product;
    private String u_product;
    private String prices;
    private int quantity;
    public CartInsert(){super();}
    public CartInsert(String id_product,String u_product,  String prices) {
        this.id_product = id_product;
        this.u_product = u_product;
        this.prices= prices;
    }
    public String getId_product() {
        return id_product;
    }

    public void setId_product(String id_product) {
        this.id_product = id_product;
    }

    public String getU_product() {
        return u_product;
    }

    public void setU_product(String u_product) {
        this.u_product = u_product;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }
    public void setQuantity(int quantity)
    {
        this.quantity=quantity;
    }
    public int getQuantity(){return quantity;}
}
