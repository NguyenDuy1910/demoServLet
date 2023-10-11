package com.example.demo112.dao.impl;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import com.example.demo112.jdbc.JDBCConnection;
import com.example.demo112.dao.UserDao;
import  com.example.demo112.models.User;





public class UserDaoImpl implements UserDao
{
public Connection conn=null;
public PreparedStatement ps=null;
public ResultSet rs = null;
        @Override
        public void insert (User user)
        {
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

            try {

                conn=new JDBCConnection().getConnection();
                ps=conn.prepareStatement(sql);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());

                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Dữ liệu đã được chèn thành công.");
                } else {
                    System.out.println("Không có bản ghi nào được chèn.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }


        @Override
        public void edit (User user){
            String sql = "UPDATE users SET email = ? , username = ?, password = ?,  role_id = ? WHERE id = ?";


            try {
                conn = new JDBCConnection().getConnection();
                ps=conn.prepareStatement(sql);

                ps.setString(1, user.getEmail());
                ps.setString(2, user.getUsername());
                ps.setString(3, user.getPassword());
                ps.setInt(5, user.getRoleId());

                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void delete ( int id){
            String sql = "DELETE FROM users WHERE id = ?";

            try {
                conn=new JDBCConnection().getConnection();

                ps=conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public User get (String username){
            String sql = "SELECT * FROM users WHERE username = ? ";


            try {
                conn=new JDBCConnection().getConnection();
                ps=conn.prepareStatement(sql);
                ps.setString(1, username);
                rs = ps.executeQuery();

                while (rs.next()) {
                    User user = new User();

                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setRoleId(Integer.parseInt(rs.getString("role_id")));

                    return user;

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public User get ( int id){
            String sql = "SELECT * FROM users WHERE id = ? ";

            try {
                conn=new JDBCConnection().getConnection();
                ps=conn.prepareStatement(sql);
                ps.setInt(1, id);
                rs = ps.executeQuery();

                while (rs.next()) {
                    User user = new User();

                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setRoleId(Integer.parseInt(rs.getString("role_id")));

                    return user;

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public List<User> getAll () {
            List<User> userList = new ArrayList<User>();
            String sql = "SELECT  username, email,password FROM users";

            try {
                conn=new JDBCConnection().getConnection();
                ps=conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    User user = new User();

//                    user.setId(rs.getInt("id"));

                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
//                    user.setAvatar(rs.getString("avatar"));
//                    user.setRoleId(Integer.parseInt(rs.getString("role_id")));

                    userList.add(user);
                }
                if (userList.isEmpty()) {
                    System.out.println("Truy vấn không tìm thấy bản ghi nào.");
                } else {
                    System.out.println("Truy vấn đã thực thi thành công và có   bản ghi.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return userList;
        }

        @Override
        public List<User> search (String keyword){
            List<User> userList = new ArrayList<User>();
            String sql = "SELECT * FROM users WHERE name LIKE ? ";

            try {
                conn=new JDBCConnection().getConnection();
                ps=conn.prepareStatement(sql);
                ps.setString(1, "%" + keyword + "%");
                rs = ps.executeQuery();

                while (rs.next()) {
                    User user = new User();

                    user.setId(rs.getInt("id"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setAvatar(rs.getString("avatar"));
                    user.setRoleId(Integer.parseInt(rs.getString("role_id")));

                    userList.add(user);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return userList;
        }


    public boolean checkExistEmail(String email)
    {
        boolean duplicate = false;
        String query = "select * from users where email = ?;";
        conn = new JDBCConnection().getConnection();
        try
        {

            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next())
            {
                duplicate = true;
            }
            ps.close();
            conn.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return duplicate;
    }

    public boolean checkExistUsername(String username)
    {
        boolean duplicate = false;
        String query = "SELECT * FROM users WHERE username = ?;";
        conn = new JDBCConnection().getConnection();

        try {

            ps = conn.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                duplicate = true;
            }

            // Đóng tất cả các tài nguyên (ResultSet, PreparedStatement, Connection)

            ps.close();
            conn.close();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return duplicate;

    }
}