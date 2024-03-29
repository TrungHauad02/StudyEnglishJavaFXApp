package com.example.englishforkids.myconnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConnection {
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/englishforkids", "root", "01092003hau");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return con;
    }
}
