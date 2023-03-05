import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;


public class main {
	
	private static final Random RANDOM = new SecureRandom();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		main main = new main();
		
		System.out.println(main.encrypt("op","233fyour text here"));
		System.out.println(main.CheckPassword("op","233fyour text here"));

	}
	//Retrieves random salt
	public static byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		secureRandom.nextBytes(salt);
		
		return salt;
	}
	//Takes password "plaintext", and hashes using salt + pepper - stores in DB. To be used when a user / business signs up.
	public boolean encrypt(String username,String plaintext) {
	String salt;
	MessageDigest sha;
	try {
		  byte[] arr = getSalt();
		  salt = new String(arr, StandardCharsets.UTF_8);
		  String pepper = "ab23foed2";
		  plaintext += salt;
		  plaintext += pepper;
		  //Store salt in the database
		  
		  sha = MessageDigest.getInstance("SHA512");
		} catch (NoSuchAlgorithmException e) {
		  throw new IllegalStateException(e.getMessage(), e);
		}

	sha.reset();
	sha.update(plaintext.getBytes());
	
	//Converting to string.
	byte[] digest = sha.digest();
	BigInteger bigInt = new BigInteger(1,digest);
	String hashtext = bigInt.toString(16);
	
	while(hashtext.length() < 32 ){
	  hashtext = "0"+hashtext;
	}
	///Writing hashed password and salt to UserLogin DB
	//openConnection();
	//st.executeUpdate("INSERT INTO `UserLogin` (`Username`, `PasswordSalt`, `PasswordHash`) VALUES ('"
    //        + username + "', '" + salt + "', '" + hashtext + "')");
	//closeConnection();
	return true;
	}
	
	//Checks whether an entered password + salt (stored in db) + pepper, matches that stored in db for any user.
	public boolean CheckPassword(String username, String Password) {
		//Function to check whether entered password matches hash in database.
		String salt;
		MessageDigest sha;
		try {
			  salt = "placeholder"; ///RETRIEVE SALT FROM DB - PUT IN TRY CATCH INCASE USERNAME NOT IN DB. ALTERNATIVELY USE IF STATEMENT WHEN CALLING CHCEKPASSWORD.

			  String pepper = "ab23foed2";
			  Password += salt;
			  Password += pepper;
			  
			  sha = MessageDigest.getInstance("SHA512");
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
		
		String passwordhash = "placeholder"; ///RETRIEVE PASSWORDHASH FROM DB - PUT IN TRY CATCH INCASE USERNAME NOT IN DB. ALTERNATIVELY USE IF STATEMENT WHEN CALLING CHCEKPASSWORD.
		//If hashtext == passwordhash in db return true, else false
		if(hashtext == passwordhash) {
			return true;
		}
		return false;
		
	}
	
}
