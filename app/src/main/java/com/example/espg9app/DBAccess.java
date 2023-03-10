package com.example.espg9app;
import java.sql.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

public class DBAccess {

    public Connection conn;
    public Statement st;
    public static String url = "jdbc:mysql://sql8.freemysqlhosting.net";
    public static String dbUsername = "sql8598438";
    public static String dbPassword = "mQ2acQ2I4h";

    //
    //helper functions ---- make sure each main function calls these once each at the start and end
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
    //helper functions for redeemVoucher ---- these will fail if run by themselves as they don't establish a DB connection
    //

    public boolean markVoucherRedeemed (int voucherClaimID) {
        try {
            st.executeUpdate("UPDATE VoucherClaims SET NumRedeemed = NumRedeemed + 1 WHERE VoucherClaimID = " + voucherClaimID);
            return true;
        }

        catch (SQLException e) {
            return false;
        }
    }

    public boolean checkVoucherInstanceExistsAndActive(int voucherClaimID) {
        ResultSet rs;
        int businessID;

        try {
            rs = st.executeQuery("SELECT * FROM `VoucherClaims` WHERE VoucherClaimID = " + voucherClaimID);

            if (!rs.next()) return false;
            else {
                businessID = rs.getInt("BusinessID");
                rs = st.executeQuery("SELECT `VoucherActive` FROM `BusinessInfo` WHERE BusinessID = " + businessID);
                rs.next();

                int active = rs.getInt("VoucherActive");
                return (active == 1);
            }
        }

        catch (SQLException e) {
            return false;
        }
    }

    public int getDiscountPercent(int voucherClaimID) {
        int maxDiscountAchieved = 0;

        try {
            ResultSet rs = st.executeQuery("SELECT * FROM `VoucherClaims` WHERE VoucherClaimID = " + voucherClaimID);
            rs.next();

            int businessID = rs.getInt("BusinessID");
            int numRedeemed = rs.getInt("NumRedeemed");

            rs = st.executeQuery("SELECT DiscountTiers FROM `BusinessInfo` WHERE BusinessID = " + businessID);
            rs.next();

            String discountTiers = rs.getString("DiscountTiers");
            String[] tiersArray = discountTiers.split(",");

            for (String s : tiersArray) {

                //converts string representation of discount tiers into integer arraylists
                Scanner scanner = new Scanner(s);
                List<Integer> tierArrayList = new ArrayList<>();
                while (scanner.hasNextInt()) {
                    tierArrayList.add(scanner.nextInt());
                }

                if (numRedeemed >= tierArrayList.get(0)) maxDiscountAchieved = tierArrayList.get(1);
            }

            return maxDiscountAchieved;
        }

        catch (SQLException e) {
            return 0;
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

            rs = st.executeQuery("SELECT * FROM `User` WHERE Username = \"" + username + "\"");
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
        int numBusinesses = 0;

        try {
            rs = st.executeQuery("SELECT * FROM `BusinessInfo`");
            if (rs == null) return null;

            ArrayList<Business> businessArray = new ArrayList<>();

            while (rs.next()) {
                Business businessToAdd = new Business();

                businessToAdd.setId(rs.getInt("BusinessID"));
                businessToAdd.setName(rs.getString("BusinessName"));
                businessToAdd.setIconPath(rs.getString("Icon"));
                businessToAdd.setTags(rs.getString("Tags"));
                businessToAdd.setDescription(rs.getString("Description"));
                businessToAdd.setSusRating(rs.getFloat("SusRating"));
                Coordinates coordToAdd = new Coordinates(rs.getFloat("Latitude"), rs.getFloat("Longitude"));
                businessToAdd.setCoordinates(coordToAdd);
                businessToAdd.setVoucherActive(rs.getInt("VoucherActive") != 0);
                businessToAdd.setDiscountTiers(rs.getString("DiscountTiers"));

                businessArray.add(businessToAdd);
                numBusinesses++;
            }

            float sumRatings;
            float numRatings;

            for (int i = 0; i < numBusinesses; i++) {
                rs = st.executeQuery("SELECT (NumberOfStars) from `Ratings` WHERE BusinessID = " + businessArray.get(i).getId());

                while (rs.next()) {
                    sumRatings += rs.getInt("NumberOfStars");
                    numRatings += 1.0;
                }

                if (numRatings == 0) {
                    businessArray.get(i).setUserRating(0);
                    businessArray.get(i).setNumReviews(0);
                }

                else {
                    businessArray.get(i).setUserRating(sumRatings / numRatings);
                    businessArray.get(i).setNumReviews((int) numRatings);
                }
            }


            closeConnection();
            return businessArray;
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    public boolean createVoucherInstance (int businessID, String username) {
        openConnection();
        ResultSet rs;

        try {
            rs = st.executeQuery("SELECT * FROM `VoucherClaims` WHERE Username = '" + username + "' AND BusinessID = " + businessID);
            if (rs.next()) return false;

            st.executeUpdate("INSERT INTO `VoucherClaims` (`BusinessID`, `Username`, `NumRedeemed`) VALUES (" + businessID + ", '" + username + "', 0)");

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
            return rs.next();
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

    public int redeemVoucher(int voucherClaimID) {
        openConnection();

        if (!checkVoucherInstanceExistsAndActive(voucherClaimID)) {
            closeConnection();
            return 0;
        }
        int discount = getDiscountPercent(voucherClaimID);
        if (discount > 0) {
            boolean redeemedSuccessfully = markVoucherRedeemed(voucherClaimID);
            if (!redeemedSuccessfully) {
                closeConnection();
                return 0;
            }
        }

        closeConnection();
        return discount;
    }

    // Encrypts plaintext password into a hash. Takes username/businessID (as string), password, and a Bool to specify whether a user or business.
    public boolean encrypt(String username,String plaintext, boolean UserTrueBusinessFalse) {
        String salt;
        MessageDigest sha;
        openConnection();
        ResultSet array;
        byte[] arr;
        byte[] SaltStr;
        String x = "temp";

        try {
            arr = getSalt();
            salt = new String(arr, StandardCharsets.UTF_8);
            String pepper = "ab23foed2";

            if(UserTrueBusinessFalse){
                st.executeUpdate("INSERT INTO `UserLogin` (`Username`, `PasswordSalt`, `PasswordHash`) VALUES ('"
                        + username + "', '" + salt + "', '" + x + "')");
                array = st.executeQuery("SELECT PasswordSalt FROM `UserLogin` WHERE Username = '" + username + "'");
            }else {
                int businessID;
                businessID = Integer.parseInt(username);
                st.executeUpdate("INSERT INTO `BusinessLogin` (`BusinessID`, `PasswordSalt`, `PasswordHash`) VALUES ('"
                        + businessID + "', '" + salt + "', '" + x + "')");
                array = st.executeQuery("SELECT PasswordSalt FROM `BusinessLogin` WHERE BusinessID = '" + businessID + "'");

            }

            array.next();
            SaltStr = array.getBytes("PasswordSalt");
            salt = new String(SaltStr, StandardCharsets.UTF_8);

            plaintext += salt;
            plaintext += pepper;

            sha = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException | SQLException e) {
            return false;
        }

        sha.reset();
        sha.update(plaintext.getBytes());

        byte[] digest = sha.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);

        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }

        try {
            if(UserTrueBusinessFalse){
                st.executeUpdate("UPDATE `UserLogin` SET PasswordHash = '" + hashtext + "'WHERE Username = '" + username + "'");
            }else{
                int businessID;
                businessID = Integer.parseInt(username);
                st.executeUpdate("UPDATE `BusinessLogin` SET PasswordHash = '" + hashtext + "'WHERE BusinessID = '" + businessID + "'");
            }
            closeConnection();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }

    //Checks whether an entered password + salt (stored in db) + pepper, matches that stored in db for any user. Takes username/businessID (as string), password, and a Bool to specify whether a user or business.
    public boolean CheckPassword(String username, String Password, boolean UserTrueBusinessFalse) {
        //Function to check whether entered password matches hash in database.
        String salt;
        MessageDigest sha;
        openConnection();
        ResultSet arr;
        ResultSet hash;
        String hashStr;
        byte[] SaltStr;

        try {
            try {
                if(UserTrueBusinessFalse){
                    arr = st.executeQuery("SELECT PasswordSalt FROM `UserLogin` WHERE Username = '" + username + "'");
                }else{
                    int businessID;
                    businessID = Integer.parseInt(username);
                    arr = st.executeQuery("SELECT PasswordSalt FROM `BusinessLogin` WHERE BusinessID = '" + businessID + "'");
                }
                arr.next();
                SaltStr = arr.getBytes("PasswordSalt");

            } catch(SQLException e) {
                return false;
            }

            salt = new String(SaltStr, StandardCharsets.UTF_8);

            String pepper = "ab23foed2";
            Password += salt;
            Password += pepper;
            sha = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }

        sha.reset();
        sha.update(Password.getBytes());

        //Converting to string.
        byte[] digest = sha.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashtext = bigInt.toString(16);

        while(hashtext.length() < 32 ){
            hashtext = "0"+hashtext;
        }

        try {
            if(UserTrueBusinessFalse) {
                hash = st.executeQuery("SELECT PasswordHash FROM `UserLogin` WHERE Username = '" + username + "'");
            }else{
                int businessID;
                businessID = Integer.parseInt(username);
                hash = st.executeQuery("SELECT PasswordHash FROM `BusinessLogin` WHERE BusinessID = '" + businessID + "'");
            }
            hash.next();
            hashStr = hash.getString("PasswordHash");
        }catch(SQLException e){
            return false;
        }

        return hashtext.equals(hashStr);

    }

    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return salt;
    }

    //
    // ----
    //

    public static void main(String[] args) {
        DBAccess dba = new DBAccess();
        ArrayList<Business> a = dba.getAllBusinesses();
        for (int i = 0; i < a.size(); i++) a.get(i).soutBusiness();
    }
}

