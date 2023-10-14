package com.example.demo112.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

    private static final String URL = "jdbc:postgresql://ec2-44-215-1-253.compute-1.amazonaws.com:5432/d4jf7kf72b4pgl";
    private static final String DRIVER = "org.postgresql.Driver";
    private static final String USERNAME = "pqmlehxcqqxdfz";
    private static final String PASSWORD = "95edfb30519fc647828ae241a409a1c7aa3d188f63a476c7af37ba887655f4d2";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
