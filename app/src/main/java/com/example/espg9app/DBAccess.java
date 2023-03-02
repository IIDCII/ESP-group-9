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
            return false;
        }
    }

    public boolean addStudentAccount(String username, String firstName, String lastName, String email, String password) {
        openConnection();

        try {
            st.executeUpdate("INSERT INTO `User` (`Username`, `Firstname`, `Lastname`, `Email`) VALUES ('"
                    + username + "', '" + firstName + "', '" + lastName + "', '" + email + "')");
            closeConnection();

            return true;
        }

        catch (SQLException e) {
            return false;
        }

    }

    public ArrayList<Business> getAllBusinesses() {
        openConnection();
        ResultSet rs;
        ResultSet rs2;

        try {
            rs = st.executeQuery("SELECT * FROM `BusinessInfo`");
            Business businessToAdd = new Business();
            ArrayList<Business> businessArray = new ArrayList<>();
            float sumRatings;
            float numRatings;

            while (rs.next()) {
                businessToAdd.setId(rs.getInt("BusinessID"));
                businessToAdd.setName(rs.getString("BusinessName"));
                businessToAdd.setIconPath(rs.getString("Icon"));
                businessToAdd.setTags(rs.getString("Tags"));
                businessToAdd.setDescription(rs.getString("Description"));
                businessToAdd.setSusRating(rs.getFloat("SusRating"));

                Coordinates coordToAdd = new Coordinates(rs.getFloat("Latitude"), rs.getFloat("Longitude"));
                businessToAdd.setCoordinates(coordToAdd);

                if (rs.getInt("VoucherActive") == 0) businessToAdd.setVoucherActive(false);
                else businessToAdd.setVoucherActive(true);

                businessToAdd.setDiscountTiers(rs.getString("DiscountTiers"));

                rs2 = st.executeQuery("SELECT (NumberOfStars) from `Ratings` WHERE BusinessID = " + rs.getInt("BusinessID"));
                sumRatings = 0;
                numRatings = 0;
                while (rs2.next()) {
                    sumRatings += rs2.getInt("NumberOfStars");
                    numRatings += 1.0;
                }
                if (numRatings == 0) businessToAdd.setUserRating(0);
                else businessToAdd.setUserRating(sumRatings / numRatings);

                businessArray.add(businessToAdd);
            }

            closeConnection();
            return businessArray;
            
        }
        catch (SQLException e) {
            return null;
        }

    }

    public boolean addBusiness(String email, String name, String iconPath, String tags, String description,
                               double susRating, Coordinates coordinates, boolean voucherActive, String discountTiers) {
        openConnection();
        int x;
        if (voucherActive) x = 1;
        else x = 0;

        try {

            st.executeUpdate("INSERT INTO `BusinessUser` (`BusinessEmail`) VALUES ('" + email + "')");
            st.executeUpdate("INSERT INTO `BusinessInfo` VALUES ((SELECT BusinessID FROM `BusinessUser` WHERE BusinessEmail = '" + email + "'), '"
                    + name + "', '" + iconPath + "', '" + tags + "', '" + description + "', " + susRating + ", "
                    + coordinates.getLatitude() + ", " + coordinates.getLongitude() + ", " + x + ", '" + discountTiers + "')");

            closeConnection();
            return true;
        }

        catch (SQLException e) {
            return false;
        }
    }

    public boolean createVoucherInstance (int businessID, String username) {
        openConnection();

        try {
            st.executeUpdate("INSERT INTO `VoucherClaims` (`BusinessID`, `Username`, `NumRedeemed`) VALUES (" + businessID + ", '" + username + "', 0)");

            closeConnection();
            return true;
        }

        catch (SQLException e) {
            return false;
        }

    }

    public boolean redeemVoucher (int voucherClaimID) {
        openConnection();

        try {
            st.executeUpdate("UPDATE VoucherClaims SET NumRedeemed = NumRedeemed + 1 WHERE VoucherClaimID = " + voucherClaimID);

            closeConnection();
            return true;
        }

        catch (SQLException e) {
            return false;
        }
    }

    public boolean deactivateVoucher(int businessID) {
        openConnection();

        try {
            st.executeUpdate("UPDATE BusinessInfo SET VoucherActive = 0 WHERE BusinessID = '" + businessID + "'");

            closeConnection();
            return true;
        }

        catch (SQLException e) {
            return false;
        }
    }

    public boolean activateVoucher(int businessID) {
        openConnection();

        try {
            st.executeUpdate("UPDATE BusinessInfo SET VoucherActive = 1 WHERE BusinessID = '" + businessID + "'");

            closeConnection();
            return true;
        }

        catch (SQLException e) {
            return false;
        }
    }

    public boolean isVoucherInstance(int businessID, String username) {
        openConnection();
        ResultSet rs;

        try {
            rs = st.executeQuery("SELECT * FROM `VoucherClaims` WHERE BusinessID = " + businessID + " AND Username = '" + username + "'");
            if (rs.next()) return true;
            else return false;
        }

        catch (SQLException e) {
            return false;
        }
    }

    public boolean changeDiscountTiers(int businessID, String newDiscountTiers) {
        openConnection();

        try {
            st.executeUpdate("UPDATE BusinessInfo SET DiscountTiers = '" + newDiscountTiers + "' WHERE BusinessID = '" + businessID + "'");

            closeConnection();
            return true;
        }

        catch (SQLException e) {
            return false;
        }
    }

    public boolean leaveReview(String username, int businessID, int numberOfStars) {
        openConnection();

        try {
            st.executeUpdate("DELETE FROM `Ratings` WHERE BusinessID = " + businessID + " AND Username = '" + username + "'");
            st.executeUpdate("INSERT INTO `Ratings` VALUES ('" + username + "', " + businessID + ", " + numberOfStars + ")");

            closeConnection();
            return true;
        }

        catch (SQLException e) {
            return false;
        }
    }

    //
    // ----
    //

    public static void main(String[] args) {
        DBAccess dba = new DBAccess();
        dba.getAllBusinesses();
    }
}

