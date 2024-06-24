package com.example.foodydbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {

    public static Connection connection;

    public PostgresConnection() {
        try {
            String url = ProtectedData.url;
            String username = ProtectedData.username;
            String password = ProtectedData.password;
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }
}
