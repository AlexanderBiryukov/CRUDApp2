package com.alexb.crudapp2.config;

import java.sql.*;

public class JdbcUtils {

    private static Connection connection;

    private static final String url = "jdbc:mysql://localhost:3306/firstDB";
    private static final String username = "root";
    private static final String password = "password";


    private static Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(url, username, password);
            }
            return connection;
        } catch (Throwable t) {
            System.out.println("Connection could not be received!");
            System.exit(1);
            throw new RuntimeException("TEST");
        }
    }

    public static PreparedStatement prepareStatement(String sql) {
        try {
            return getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static PreparedStatement prepareStatementWithKeys(String sql) {
        try {
            return getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
