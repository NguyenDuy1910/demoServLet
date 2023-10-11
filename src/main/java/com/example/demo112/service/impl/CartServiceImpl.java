//package com.example.demo112.service.impl;
//
//import java.io.File;
//import java.util.List;
//
//import com.example.demo112.dao.CartDao;
//import com.example.demo112.models.Cart;
////import com.example.demo112.dao.ProductDao;
//import com.example.demo112.dao.UserDao;
//import com.example.demo112.dao.impl.CartDaoImpl;
//import com.example.demo112.service.CartService;
//
//
//public class CartServiceImpl implements CartService {
//    CartDao cartDao = new CartDaoImpl();
//
//    @Override
//    public List<Cart> search(String name) {
//        return cartDao.search(name);
//    }
//
//    @Override
//    public void insert(Cart cart) {
//        cartDao.insert(cart);
//
//    }
//
//    @Override
//    public List<Cart> getAll() {
//        return cartDao.getAll();
//    }
//
//    @Override
//    public Cart get(int id) {
//        return cartDao.get(id);
//    }
//
//    @Override
//    public void edit(Cart newCart) {
//        Cart oldCart = cartDao.get(newCart.getId());
//
//        oldCart.setBuyDate(newCart.getBuyDate());
//        oldCart.setBuyer(newCart.getBuyer());
//
//
//        cartDao.edit(oldCart);
//
//    }
//
//    @Override
//    public void delete(int id) {
//        cartDao.delete(id);
//    }
//}
//
