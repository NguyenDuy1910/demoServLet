package com.example.demo112.service.impl;

import com.example.demo112.dao.InsertDao;
import com.example.demo112.dao.impl.InsertDaoImpl;
import com.example.demo112.models.CartInsert;
import com.example.demo112.service.InsertService;

import java.util.List;

public class InsertServiceImpl implements InsertService {
    InsertDao insertDao=new InsertDaoImpl();

    @Override
    public void insert(CartInsert cart) {
        insertDao.add(cart);
    }

    @Override
    public boolean delete(String id_product) {
        insertDao.delete(id_product);
        return true;
    }

    @Override
    public List<CartInsert> getAll() {
        return insertDao.getAll();
    }
    @Override
    public boolean insertion(String id_product,String u_product,String prices)
    {
        insertDao.add(new CartInsert(id_product,u_product,prices));
        return true;
    }
//    @Override
//    public int checkIdProduct(String id_product){
//        return insertDao.checkIdProduct(id_product);
//
//    }





}
