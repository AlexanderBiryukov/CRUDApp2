package com.alexb.crudapp2.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDataSource {

    private final String url;
    private final String username;
    private final String password;

    public MyDataSource() {
        this.url = "jdbc:mysql://localhost:3306/firstDB";
        this.username = "root";
        this.password = "password";
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
