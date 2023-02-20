package com.example.espg9app;
import java.sql.*;

public class DBAccess {

    public Connection conn;
    public Statement st;
    public static String url = "jdbc:mysql://sql8.freemysqlhosting.net";
    public static String dbUsername = "sql8598438";
    public static String dbPassword = "mQ2acQ2I4h";

    public void openConnection() {
        try {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            st = conn.createStatement();
            st.executeQuery("USE sql8598438");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
                /* ignore close errors */ }
        }
    }
    public Boolean emailInDB(String email) {
        openConnection();
        ResultSet rs;
        try {
            rs = st.executeQuery("SELECT * FROM `tbl_studentInfo` WHERE email = \"jamiebrine@yahoo.co.uk\"");
            if (rs.next()) return true;
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        DBAccess dba = new DBAccess();
        System.out.println(dba.emailInDB("jamiebrine@yahoo.co.uk"));
        dba.closeConnection();
    }
}

