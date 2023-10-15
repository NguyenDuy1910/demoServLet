package com.example.demo112.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.example.demo112.jdbc.JDBCConnection;
import com.example.demo112.dao.UserDao;
import com.example.demo112.models.User;

public class UserDaoImpl implements UserDao {
    private Connection conn;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    public UserDaoImpl() {
        conn = JDBCConnection.getInstance().getConnection();
    }

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";

        try {

            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Data has been inserted successfully.");
            } else {
                System.out.println("No records were inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    @Override
    public void edit(User user) {
        String sql = "UPDATE users SET email = ?, username = ?, password = ?, role_id = ? WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getRoleId());
            ps.setInt(5, user.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }

    @Override
    public User get(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAvatar(rs.getString("avatar"));
                user.setRoleId(rs.getInt("role_id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    @Override
    public User get(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRoleId(rs.getInt("role_id"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT username, email, password FROM users";

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                userList.add(user);
            }

            if (userList.isEmpty()) {
                System.out.println("No records found.");
            } else {
                System.out.println("Query executed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return userList;
    }

    @Override
    public List<User> search(String keyword) {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE username LIKE ?";

        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRoleId(rs.getInt("role_id"));
                userList.add(user);
            }

            if (userList.isEmpty()){
                System.out.println("No records found.");
            } else {
                System.out.println("Query executed successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return userList;
    }
    public boolean checkExistEmail(String email)
    {
        boolean duplicate = false;
        String query = "select * from users where email = ?;";
        try
        {
            if(conn!=null) {
                ps = conn.prepareStatement(query);
                ps.setString(1, email);
                rs = ps.executeQuery();
                if (rs.next()) {
                    duplicate = true;
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            closeResources();
        }
        return duplicate;
    }

    public boolean checkExistUsername(String username) {
        boolean duplicate = false;
        String query = "SELECT * FROM users WHERE username = ?;";

        try {
            if(conn!=null){
                ps = conn.prepareStatement(query);
                ps.setString(1, username);
                rs = ps.executeQuery();

            if (rs.next()) {
                duplicate = true;
            }}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        return duplicate;
    }

    private void closeResources() {
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