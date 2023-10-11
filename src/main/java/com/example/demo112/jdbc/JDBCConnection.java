package com.example.demo112.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

    private static final String URL = "jdbc:mysql://jtb9ia3h1pgevwb1.cbetxkdyhwsb.us-east-1.rds.amazonaws.com";

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver"; // Đảm bảo sử dụng driver của MySQL
    private static final String USERNAME = "vs7gi59do10b9t0v"; // Tên người dùng từ URL
    private static final String PASSWORD = "k16wgjwovj4wsy7q"; // Mật khẩu từ URL

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;

    }
}