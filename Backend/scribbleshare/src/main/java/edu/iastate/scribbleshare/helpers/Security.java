package edu.iastate.scribbleshare.helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Security {
    
    public static final Logger logger = LoggerFactory.getLogger(Security.class);

    /**
     * Hashes a given password and returns the salt and the hashed string (with a space between). 
     * Can be used in two situations. 
     * 1. Storing a user's password on account creation
     * 2. Hashing a user's password when they try to login to compare to the 
     * hashed value in the database
     * @param password 
     * @return
     */
    public static String generateHash(String password){
        SecureRandom secureRandom = new SecureRandom();
        String salt = secureRandom.generateSeed(12).toString();
        return salt + " " + getHash(salt.toString(), password);
    } 

    /**
     * Generates a hash with the given salt and password. Can be used to generate a new password hash or check login.
     * @param salt
     * @param password
     * @return The hash using the given salt and password
     */
    private static String getHash(String salt, String password){
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 10, 512);
        try{
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = skf.generateSecret(pbeKeySpec).getEncoded();
            return Base64.getMimeEncoder().encodeToString(hash);
        }
        catch(NoSuchAlgorithmException e){
            logger.error("Unknown hashing algorithm");
            e.printStackTrace();
        }
        catch(InvalidKeySpecException ee){
            logger.error("Invalid key spec");
            ee.printStackTrace();
        }
        return null;
    }

    /**
     * Return if the given password's hash matches the stored password (stored passwords are already hashed)
     * @param storedPassword Salt + hash stored in the database
     * @param givenPassword Attempted login password
     * @return
     */
    public static boolean checkHash(String storedPassword, String givenPassword){
        String[] saltHash = storedPassword.split(" ");
        String storedSalt = saltHash[0];
        String storedHash = saltHash[1];
        String newHash = getHash(storedSalt, givenPassword);
        return newHash.equals(storedHash);
    }

}
