package com.example.demo112.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.demo112.dao.InsertDao;
import com.example.demo112.jdbc.JDBCConnection;
import com.example.demo112.models.CartInsert;

public class InsertDaoImpl implements InsertDao {

    private Connection con;

    public InsertDaoImpl() {
        con = JDBCConnection.getInstance().getConnection();
    }

    @Override
    public void add(CartInsert cart) {
        String selectQuery = "SELECT * FROM cart WHERE id_product = ?";
        String updateQuery = "UPDATE cart SET quantity = ? WHERE id_product = ?";
        String insertQuery = "INSERT INTO cart (id_product, u_product, prices, quantity) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
            ps = con.prepareStatement(selectQuery);
            ps.setString(1, cart.getId_product());
            rs = ps.executeQuery();

            if (rs.next()) {
                // Sản phẩm đã tồn tại trong giỏ hàng
                int existingQuantity = rs.getInt("quantity");
                int newQuantity = existingQuantity + 1;

                // Cập nhật số lượng sản phẩm
                PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                updateStatement.setInt(1, newQuantity);
                updateStatement.setString(2, cart.getId_product());
                updateStatement.executeUpdate();
            } else {
                // Sản phẩm chưa tồn tại trong giỏ hàng
                PreparedStatement insertStatement = con.prepareStatement(insertQuery);
                insertStatement.setString(1, cart.getId_product());
                insertStatement.setString(2, cart.getU_product());
                insertStatement.setString(3, cart.getPrices());
                insertStatement.setInt(4, 1); // Assuming quantity starts from 1
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }
    }

    @Override
    public void delete(String id_product) {
        String sql = "DELETE FROM cart WHERE id_product = ?";

        PreparedStatement ps = null;

        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, id_product);
            System.out.println(id_product);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product delete successfull.");
            } else {
                System.out.println("No find product");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(null, ps);
        }
    }

    @Override
    public List<CartInsert> getAll() {
        List<CartInsert> cartList = new ArrayList<CartInsert>();

        String sql = "SELECT id_product, u_product, prices, quantity FROM cart";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                CartInsert cart = new CartInsert();
                cart.setId_product(rs.getString("id_product"));
                cart.setU_product(rs.getString("u_product"));
                cart.setPrices(rs.getString("prices"));
                cart.setQuantity(rs.getInt("quantity"));
                cartList.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps);
        }

        return cartList;
    }

    // Utility method to close resources (ResultSet and PreparedStatement)
    private void closeResources(ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}