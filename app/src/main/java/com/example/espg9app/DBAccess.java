package com.example.espg9app;
import java.sql.*;

public class DBAccess {

    public Connection conn;
    public Statement st;
    public static String url = "jdbc:mysql://sql8.freemysqlhosting.net";
    public static String dbUsername = "sql8598438";
    public static String dbPassword = "mQ2acQ2I4h";

    //
    //helper functions ---- make sure each function calls these once each at the start and end
    //
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
               throw new RuntimeException(e);
            }
        }
    }

    //
    //main functions ---- add all the specific use cases under here
    //

    public boolean checkStudentAccountValid(String email, String username) {
        openConnection();
        ResultSet rs;

        try {
            rs = st.executeQuery("SELECT * FROM `User` WHERE Email = \"" + email + "\"");
            if (rs.next()) {
                this.closeConnection();
                return false;
            }

            rs = st.executeQuery("SELECT * FROM `User` WHERE Username = \"" + email + "\"");
            if (rs.next()) {
                this.closeConnection();
                return false;
            }

            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addStudentAccount(String username, String firstName, String lastName, String email, String password) {
        openConnection();

        try {
            st.executeUpdate("INSERT INTO `User` (`Username`, `Firstname`, `Lastname`, `Email`) VALUES " +
                    "('" + username + "', '" + firstName + "', '" + lastName + "', '" + email + "')");
            this.closeConnection();
            return true;
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //
    // ----
    //

    public static void main(String[] args) {
        DBAccess dba = new DBAccess();
//        test whatever you like in here
//        if (dba.checkStudentAccountValid("alexanderager@yahoo.co.uk", "alex456")) {
//            System.out.println(dba.addStudentAccount("alex456", "Alex", "Ager", "alexanderager@gmail.com", "Alex456!"));
//        }
    }
}

