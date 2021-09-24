package edu.iastate.scribbleshare.helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Security {
    

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
        byte[] salt = secureRandom.generateSeed(12);
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), salt, 10, 512);
        try{
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            byte[] hash = skf.generateSecret(pbeKeySpec).getEncoded();
            return Base64.getMimeEncoder().encodeToString(salt) + " " +
                Base64.getMimeEncoder().encodeToString(hash);
        }
        catch(NoSuchAlgorithmException e){
            //TODO handle
            e.printStackTrace();
        }
        catch(InvalidKeySpecException ee){
            //TODO handle
            ee.printStackTrace();
        }
        return null;
    } 

}
