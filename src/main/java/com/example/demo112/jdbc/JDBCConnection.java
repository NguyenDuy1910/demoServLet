package com.example.demo112.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {
    private static Connection connection;

    /**
     * Entry point of the application. Opens and closes the database connection.
     *
     * @param args (not used)
     * @throws SQLException if an error occurs when interacting with the database
     */
    public static void main(String[] args) throws SQLException {
        try {
            initDatabaseConnection();

        } finally {
            if (connection != null) {
                closeDatabaseConnection();
            }
        }
    }

    private static void initDatabaseConnection() throws SQLException {
        System.out.println("Connecting to the database...");
        connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/shopapp",
                "root", "");
        System.out.println("Connection valid: " + connection.isValid(5));
		/*
		If you are using MariaDB SkySQL (https://mariadb.com/products/skysql),
		enable SSL and specify the path to the CA chain file that you can download
		from the SkySQL Portal (https://cloud.mariadb.com):
			jdbc:mariadb://demo-db0000xxxx.mdb000xxxx.db.skysql.net:5047/demo?useSsl=true&serverSslCert=/path/to/your/skysql_chain.pem
		*/
    }

    private static void closeDatabaseConnection() throws SQLException {
        System.out.println("Closing database connection...");
        connection.close();
        System.out.println("Connection valid: " + connection.isValid(5));
    }
}