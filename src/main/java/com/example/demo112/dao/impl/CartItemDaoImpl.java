//package com.example.demo112.dao.impl;
//
//import com.example.demo112.dao.CartItemDao;
//import com.example.demo112.jdbc.JDBCConnection;
//import com.example.demo112.dao.UserDao;
//import com.example.demo112.service.CartService;
//import com.example.demo112.service.impl.CartServiceImpl;
//import com.example.demo112.models.CartItem;
//import com.example.demo112.service.ProductService;
//import com.example.demo112.service.impl.ProductServiceImpl;
//import java.util.List;
//
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//public class CartItemDaoImpl implements CartItemDao  {
//    CartService cartService = new CartServiceImpl();
//    ProductService productService = new ProductServiceImpl();
//    UserDao userDao = new UserDaoImpl();
//    public Connection con=null;
//    public PreparedStatement ps=null;
//    public ResultSet rs=null;
//
//    @Override
//    public void insert(CartItem cartItem) {
////        String sql = "INSERT INTO CartItem(id, cat_id, pro_id, quantity, unitPrice) VALUES (?,?,?,?,?)";
//
//        try {
//            con=new JDBCConnection().getConnection();
//
//            ps=con.prepareStatement(sql);
//
//
//            ps.setString(1, cartItem.getId());
//            ps.setString(2, cartItem.getCart().getId());
//            ps.setInt(3, cartItem.getProduct().getId());
//            ps.setInt(4, cartItem.getQuantity());
//            ps.setLong(5, cartItem.getUnitPrice());
//
//            ps.executeUpdate();
//
////			ResultSet generatedKeys = ps.getGeneratedKeys();
////			if (generatedKeys.next()) {
////				int id = generatedKeys.getInt(1);
////				cartItem.setId(id);
////			}
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
////
////    @Override
////    public void edit(CartItem cartItem) {
//////        String sql = "UPDATE CartItem SET cat_id = ?, pro_id = ?, quantity = ?, unitPrice=? WHERE id = ?";
////        Connection con = super.getJDBCConnection();
////
////        try {
////            PreparedStatement ps = con.prepareStatement(sql);
////
////            ps.setString(1, cartItem.getCart().getId());
////            ps.setInt(2, cartItem.getProduct().getId());
////            ps.setInt(3, cartItem.getQuantity());
////            ps.setLong(4, cartItem.getUnitPrice());
////            ps.setString(5, cartItem.getId());
////
////
////            ps.executeUpdate();
////
////
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////    }
////
////    @Override
////    public void delete(String id) {
////        String sql = "DELETE FROM CartItem WHERE id = ?";
////        Connection con = super.getJDBCConnection();
////
////        try {
////            PreparedStatement ps = con.prepareStatement(sql);
////            ps.setString(1, id);
////            ps.executeUpdate();
////        } catch (SQLException e) {
////            //
////            e.printStackTrace();
////        }
////    }
////
////    @Override
////    public CartItem get(int id) {
////        String sql = "SELECT " +
////                "CartItem.id, " +
////                "CartItem.quantity, " +
////                "CartItem.unitPrice, " +
////                "cart.u_id, " +
////                "cart.buyDate, " +
////                "product.name, " +
////                "product.price " +
////                "FROM CartItem " +
////                "INNER JOIN Cart " +
////                "ON CartItem.cart_id = cart.id " +
////                "INNER JOIN Product " +
////                "ON CartItem.pro_id = Product.id " +
////                "WHERE CartItem.id = ?";
////        Connection con = super.getJDBCConnection();
////
////        try {
////            PreparedStatement ps = con.prepareStatement(sql);
////            ps.setInt(1, id);
////            ResultSet rs = ps.executeQuery();
////
////            while (rs.next()) {
////                User user = userDao.get(rs.getInt("u_id"));
////
////                Cart cart = new Cart();
////                cart.setBuyer(user);
////                cart.setBuyDate(rs.getDate("buyDate"));
////
////                Product product = new Product();
////                product.setName(rs.getString("name"));
////                product.setPrice(rs.getLong("price"));
////
////                CartItem cartItem = new CartItem();
////                cartItem.setCart(cart);
////                cartItem.setProduct(product);
////                cartItem.setQuantity(rs.getInt("quantity"));
////                cartItem.setUnitPrice(rs.getLong("unitPrice"));
////
////
////                return cartItem;
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        return null;
////    }
////
////    @Override
////    public List<CartItem> getAll() {
////        List<CartItem> cartItemList = new ArrayList<CartItem>();
////        String sql = "SELECT " +
////                "CartItem.id, " +
////                "CartItem.quantity, " +
////                "CartItem.unitPrice, " +
////                "cart.u_id, " +
////                "cart.buyDate, " +
////                "product.name, " +
////                "product.price " +
////                "FROM CartItem " +
////                "INNER JOIN Cart " +
////                "ON CartItem.cat_id = Cart.id " +
////                "INNER JOIN Product " +
////                "ON CartItem.pro_id = Product.id ";
////        Connection con = super.getJDBCConnection();
////
////        try {
////            PreparedStatement ps = con.prepareStatement(sql);
////            ResultSet rs = ps.executeQuery();
////
////            while (rs.next()) {
////                User user = userDao.get(rs.getInt("u_id"));
////
////                Cart cart = new Cart();
////                cart.setBuyer(user);
////                cart.setBuyDate(rs.getDate("buyDate"));
////
////                Product product = new Product();
////                product.setName(rs.getString("name"));
////                product.setPrice(rs.getLong("price"));
////
////                CartItem cartItem = new CartItem();
////                cartItem.setId(rs.getString("id"));
////                cartItem.setCart(cart);
////                cartItem.setProduct(product);
////                cartItem.setQuantity(rs.getInt("quantity"));
////                cartItem.setUnitPrice(rs.getLong("unitPrice"));
////
////                cartItemList.add(cartItem);
////
////            }
////        } catch (SQLException e) {
////            e.printStackTrace();
////        }
////        return cartItemList;
////    }
////
////    public List<CartItem> search(String name) {
////        return null;
////    }
////
////    @Override
////    public CartItem get(String name) {
////        return null;
////    }
//}
//
//
