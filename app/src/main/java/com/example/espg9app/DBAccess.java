package com.example.espg9app;
import java.sql.*;
import java.util.ArrayList;

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
                closeConnection();
                return false;
            }

            rs = st.executeQuery("SELECT * FROM `User` WHERE Username = \"" + email + "\"");
            if (rs.next()) {
                closeConnection();
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
            st.executeUpdate("INSERT INTO `User` (`Username`, `Firstname`, `Lastname`, `Email`) VALUES ('"
                    + username + "', '" + firstName + "', '" + lastName + "', '" + email + "')");
            
            //Add in calculations for password salt and hash here
            
            
            st.executeUpdate("INSERT INTO `UserLogin` (`Username`, `PasswordSalt`, `PasswordHash`) VALUES ('"
                    + username + "', '" + PasswordSalt + "', '" + PasswordHash + "')");
            closeConnection();

            return true;
        }


    }

    public ArrayList<Business> getAllBusinesses() {
        openConnection();
        ResultSet rs;

        try {
            rs = st.executeQuery("SELECT * FROM `BusinessInfo`");
            Business businessToAdd = new Business();
            ArrayList<Business> businessArray = new ArrayList<>();

            while (rs.next()) {
                businessToAdd.setName(rs.getString("BusinessName"));
                businessToAdd.setIconPath(rs.getString("Icon"));
                businessToAdd.setTags(rs.getString("Tags"));
                businessToAdd.setDescription(rs.getString("Description"));
                businessToAdd.setSusRating(rs.getFloat("SusRating"));

                Coordinates coordToAdd = new Coordinates(rs.getDouble("Latitude"), rs.getDouble("Longitude"));
                businessToAdd.setCoordinates(coordToAdd);

                businessArray.add(businessToAdd);
            }

            closeConnection();
            return businessArray;
            
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean addBusiness(String email, String name, String iconPath, String tags, String description, double susRating, Coordinates coordinates) {
        openConnection();

        try {

            st.executeUpdate("INSERT INTO `BusinessUser` (`BusinessEmail`) VALUES ('" + email + "')");
            st.executeUpdate("INSERT INTO `BusinessInfo` VALUES ((SELECT BusinessID FROM `BusinessUser` WHERE BusinessEmail = '" + email + "'), '"
                    + name + "', '" + iconPath + "', '" + tags + "', '" + description + "', " + susRating + ", "
                    + coordinates.getLatitude() + ", " + coordinates.getLongitude() + ")");

            closeConnection();
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
    }
}

