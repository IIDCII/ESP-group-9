package com.example.espg9app;
import java.sql.*;

public class DBAccess {
    public static void main(String[] args) {
        Connection conn = null;

        System.out.println("Working");

        try {
            String url = "jdbc:mysql://sql8.freemysqlhosting.net";
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection(url, "sql8598438", "mQ2acQ2I4h");
            System.out.println("Database connection established");
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Database connection terminated");
                } catch (Exception e) {
                    /* ignore close errors */ }
            }
        }
    }
}

