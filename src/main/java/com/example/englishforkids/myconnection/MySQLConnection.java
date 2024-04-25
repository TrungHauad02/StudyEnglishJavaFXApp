package com.example.englishforkids.myconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbUrl = System.getenv("DB_URL");
            String dbUsername = System.getenv("DB_USERNAME");
            String dbPassword = System.getenv("DB_PASSWORD");
            con = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return con;
    }
}
