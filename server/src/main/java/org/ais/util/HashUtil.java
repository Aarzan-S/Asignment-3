package org.ais.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class HashUtil {
    public static String generateHash(String password) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = messageDigest.digest(password.getBytes());
            String hashString = Base64.getEncoder().encodeToString(encodedHash);
            return hashString;
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Could not generate password hash");
        }
        return null;
    }

    /**
     * Validates password with hash
     *
     * @param password password to be validated
     * @param hash     hash to be validated against
     * @return true if hash matches else false
     */
    public static boolean verifyHash(String password, String hash) {
        String passwordHash = generateHash(password);
        return hash.equals(passwordHash);
    }
}
