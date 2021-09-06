package com.delivery.delivery.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PasswordEncoder {

    public static String encodePassword(String password) {
        MessageDigest digest = null;
        byte[] hash;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "An exception was thrown", e);
        }
        if(digest != null)
            hash = digest.digest();
        else
            throw new NullPointerException();
        StringBuilder res = new StringBuilder();
        String hexTmp;
        for(byte b : hash){
            hexTmp = Integer.toHexString(b & 0xFF);
            if(hexTmp.length() == 1)
                res.append("0");
            res.append(hexTmp);
        }
        return res.toString().toUpperCase(Locale.ENGLISH);
    }

}
