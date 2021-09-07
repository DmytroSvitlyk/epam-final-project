package com.delivery.delivery.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import org.apache.log4j.Logger;

public class PasswordEncoder {

    private static final Logger logger = Logger.getLogger(PasswordEncoder.class);

    public static String encodePassword(String password) {
        MessageDigest digest = null;
        byte[] hash;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            logger.warn("An exception was thrown while encoding password");
            throw new UtilException(e);
        }
        hash = digest.digest();
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
