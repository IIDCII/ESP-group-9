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

    /**
     * Establishes a connection to the main database
     */
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

    /**
     * Closes the database connection if it exists
     */
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
        }
    }

    /**
     * Increments the number of times a voucher instance has been redeemed in the database
     * NOTE - this method will fail if not called with an already open DB connection as it does not
     * itself open it
     *
     * @param voucherClaimID ID of the voucher instance in the database
     * @return               Success of updating the database
     */
    public boolean markVoucherRedeemed (int voucherClaimID) {
        try {
            st.executeUpdate("UPDATE VoucherClaims SET NumRedeemed = NumRedeemed + 1 WHERE VoucherClaimID = " + voucherClaimID);
            return true;
        }

        catch (SQLException e) {
            return false;
        }
    }

    /**
     * Checks that the given voucher instance corresponds to a currently active voucher
     * NOTE - this method will fail if not called with an already open DB connection as it does not
     * itself open it
     *
     * @param voucherClaimID ID of the voucher instance in the database
     * @return               Active status of the corresponding voucher, False if such a voucher
     *                       does not exist
     */
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

    /**
     * Given an active voucher, calculates the percentage discount the user will receive
     *
     * @param voucherClaimID ID of the voucher instance in the database
     * @return               Percentage discount as an integer; 0 if the operation fails
     */
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

    /**
     * Checks if a username and email address have been used in the database already
     * NOTE - this method will fail if not called with an already open DB connection as it does not
     * itself open it
     *
     * @param email    Email address to check
     * @param username Username to check
     * @return         True if neither the username or email is currently in the database, false
     *                 otherwise
     */
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
            closeConnection();
            return false;
        }
    }

    /**
     * Adds a record to the User table containing all of their personal nonsensitive information
     *
     * @param username  User's chosen username
     * @param firstName User's first name
     * @param lastName  User's last name
     * @param email     User's email address
     * @return          True if record was created successfully, false if not
     */
    public boolean addStudentAccount(String username, String firstName, String lastName, String email) {
        openConnection();

        try {
            st.executeUpdate("INSERT INTO `User` (`Username`, `Firstname`, `Lastname`, `Email`) VALUES ('"
                    + username + "', '" + firstName + "', '" + lastName + "', '" + email + "')");
            closeConnection();

            return true;
        }

        catch (SQLException e) {
            closeConnection();
            return false;
        }

    }

    /**
     * Fetches all of the public business information from each business, including vouchers and the
     * average user ratings
     *
     * @return ArrayList of businesses
     */
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
                businessToAdd.setVoucherDescription(rs.getString("VoucherDescription"));

                businessArray.add(businessToAdd);
                numBusinesses++;
            }

            float sumRatings = 0;
            float numRatings = 0;

            for (int i = 0; i < numBusinesses; i++) {
                rs = st.executeQuery("SELECT (NumberOfStars) from `Ratings` WHERE BusinessID = " + businessArray.get(i).getId());

                while (rs.next()) {
                    //sumRatings += rs.getInt("NumberOfStars");
                    //numRatings += 1.0;
                }


            }


            closeConnection();
            return businessArray;
        }

        catch (SQLException e) {
            closeConnection();
            throw new RuntimeException(e);
        }

    }

    /**
     * Adds a record to the BusinessInfo table containing all of their a business' public information,
     * and a record in the BusinessUser table which will generate a unique business ID with which the
     * business will be referenced throughout
     *
     * @param email              Email address of the business
     * @param name               Name of the business
     * @param iconPath           File path of the icon that will be displayed alongside the business info
     *                           to the user, relative to the assets folder
     * @param tags               String of words that describe the business, with which a user will be able
     *                           to filter businesses; separated by spaces
     * @param description        Text description of the business that will be displayed to the user
     * @param susRating          Sustainability rating decided by our bespoke algorithm, out of 5
     * @param coordinates        Set of latitude/ longitude coordinates pointing to the location at which
     *                           the voucher can be redeemed
     * @param voucherActive      Whether the voucher is currently available to be used
     * @param discountTiers      Discount that the voucher will grant the user;
     *                           in the form T1 D1,T2 D2,...,Tn Dn, where Tk is the number of times a user
     *                           must redeem this voucher to receive Dk% discount;
     *                           T1 is always 0
     * @param voucherDescription Text description of what the voucher can be used for
     * @return                   True if record created successfully, false otherwise
     */
    public boolean addBusiness(String email, String name, String iconPath, String tags, String description,
                               double susRating, Coordinates coordinates, boolean voucherActive, String discountTiers, String voucherDescription) {
        openConnection();
        int x;
        if (voucherActive) x = 1;
        else x = 0;

        try {

            st.executeUpdate("INSERT INTO `BusinessUser` (`BusinessEmail`) VALUES ('" + email + "')");
            st.executeUpdate("INSERT INTO `BusinessInfo` VALUES ((SELECT BusinessID FROM `BusinessUser` WHERE BusinessEmail = '" + email + "'), '"
                    + name + "', '" + iconPath + "', '" + tags + "', '" + description + "', " + susRating + ", "
                    + coordinates.getLatitude() + ", " + coordinates.getLongitude() + ", " + x + ", '" + discountTiers + "', '" + voucherDescription + "')");


            closeConnection();
            return true;
        }

        catch (SQLException e) {
            closeConnection();
            throw new RuntimeException(e);
        }
    }

    /**
     * Adds a record to the VoucherClaims table which represents a user adding that voucher to their
     * wallet; does nothing if a record for that user and business already exists
     *
     * @param businessID ID of the business that the voucher is for
     * @param username   Username of the user that is adding the voucher to their wallet
     * @return           True if instance was created, false if it already existed/ an SQL error occured
     */
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
            closeConnection();
            return false;
        }

    }

    /**
     * Removes a voucher instance from the database, thus removing it from that user's wallet
     *
     * @param businessID ID of the business that the voucher is for
     * @param username   Username of the user that is removing the voucher from their wallet
     * @return           True if removed successfully, false otherwise
     */
    public boolean deleteVoucherInstance (int businessID, String username) {
        openConnection();

        try {
            st.executeUpdate("DELETE FROM `VoucherClaims` WHERE Username = '" + username + "' AND BusinessID = " + businessID);

            closeConnection();
            return true;
        }

        catch (SQLException e) {
            closeConnection();
            return false;
        }
    }

    /**
     * Sets the voucher for a specified business to inactive
     *
     * @param businessID Business for which the voucher will be deactivated
     * @return           True if deactivated successfully, false otherwise
     */
    public boolean deactivateVoucher(int businessID) {
        openConnection();

        try {
            st.executeUpdate("UPDATE BusinessInfo SET VoucherActive = 0 WHERE BusinessID = '" + businessID + "'");

            closeConnection();
            return true;
        }

        catch (SQLException e) {
            closeConnection();
            return false;
        }
    }

    /**
     * Sets the voucher for a specified business to active
     *
     * @param businessID Business for which the voucher will be activated
     * @return           True if deactivated successfully, false otherwise
     */
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

    /**
     * Checks if a user has a certain voucher in their wallet
     * @param businessID ID of business that voucher is for
     * @param username   Username
     * @return           True if voucher is in wallet, false otherwise
     */
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

    /**
     * Allows a business owner to edit the amount of discount their voucher will grant its users
     *
     * @param businessID       ID of the business for which the discount tiers will be changed
     * @param newDiscountTiers Discount that the voucher will grant the user;
     *      *                  in the form T1 D1,T2 D2,...,Tn Dn, where Tk is the number of times a user
     *      *                  must redeem this voucher to receive Dk% discount;
     *      *                  T1 is always 0
     * @return                 True if updated successfully, false otherwise
     */
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

    /**
     * Adds a record to the Reviews table which represents a user reviewing a certain business; if a
     * review from that user for that business already exists, overwrites it
     * @param username      User leaving the review
     * @param businessID    ID of business which the user is reviewing
     * @param numberOfStars Integer from 0 to 5
     * @return              True if record was successfully created, false otherwise
     */
    public boolean leaveReview(String username, int businessID, int numberOfStars) {
        if (numberOfStars > 5 || numberOfStars < 0) return false;

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

    /**
     * Method to be run when a business owner scans a voucher; calls checkVoucherInstanceExistsAndActive,
     * calculateDiscountPercent, and markVoucherRedeemed
     *
     * @param voucherClaimID Voucher instance to be redeemed
     * @return               Integer representing percentage discount to be applied, 0 if voucher is
     *                       inactive or an error occurs
     */
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

    /**
     * Encrypts plaintext password into a hash
     *
     * @param username              Username of student/ string representation of businessID
     * @param plaintext             Password as entered by user
     * @param UserTrueBusinessFalse True if account is user account, false if it is a business
     * @return                      True if password hashed and stored successfully, false otherwise
     */
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
            closeConnection();
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
            closeConnection();
            return false;
        }

    }

    /**
     * Checks whether an entered password + salt (stored in db) + pepper, matches that stored in db
     * for any user
     *
     * @param username              Username of student/ string representation of businessID
     * @param Password              Password as entered by user
     * @param UserTrueBusinessFalse True if account is user account, false if it is a business
     * @return                      True if password matches the information stored in the database,
     *                              false otherwise
     */
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
                closeConnection();
                return false;
            }

            salt = new String(SaltStr, StandardCharsets.UTF_8);

            String pepper = "ab23foed2";
            Password += salt;
            Password += pepper;
            sha = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            closeConnection();
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
            closeConnection();
            return false;
        }

        return hashtext.equals(hashStr);

    }

    /**
     * Generates the salt that will be used in hashing passwords
     *
     * @return                          Salt
     * @throws NoSuchAlgorithmException Sometimes lol idk alex wrote this
     */
    public static byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);

        return salt;
    }

    public static void main(String[] args) {
        DBAccess dba = new DBAccess();
        System.out.println(dba.addBusiness("jamiebusiness@gmail.com", "Jamie's XXX Paradise", "handcuffs.jpg", "whips chains", "Need i say more???? Exquisite exquisite exquisite exquisite exquisite!", 4.69, new Coordinates((float) 32.4564, (float) 11.8595), true, "0 20, 15 25, 100 30", "This voucher is valid on all self pleasure accessories excluding beads"));
    }
}