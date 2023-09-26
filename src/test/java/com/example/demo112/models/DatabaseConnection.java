package com.example.demo112.models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// This class can be used to initialize the database connection
public class DatabaseConnection {
    protected static Connection initializeDatabase()
            throws SQLException, ClassNotFoundException
    {
        // Initialize all the information regarding
        // Database Connection
        String dbDriver = "com.mysql.cj.jdbc.Driver";
        String dbURL = "jdbc:mysql:// localhost:3306/shopapp";
        // Database name to access
        String dbName = "shopapp";
        String dbUsername = "root";
        String dbPassword = "";

        Class.forName(dbDriver);
        Connection con = DriverManager.getConnection(dbURL + dbName,
                dbUsername,
                dbPassword);
        return con;
    }
    public static boolean testConnection() {
        Connection con = null;
        try {
            con = initializeDatabase();
            return true; // Trả về true nếu kết nối thành công
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // In lỗi nếu kết nối không thành công
            return false; // Trả về false nếu kết nối không thành công
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}