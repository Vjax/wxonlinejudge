package com.weijiax.util;

import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.util.logging.Logger;

public class MD5Util {

    private static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);

    public static String encode(String str){
        MessageDigest md = null;
        BASE64Encoder encoder = null;
        String encodedStr = null;
        try {
            md = MessageDigest.getInstance("MD5");
            encoder = new BASE64Encoder();
            encodedStr = encoder.encode(md.digest(str.getBytes("UTF-8")));
        }catch (Exception e){
            LOGGER.error("encode string using MD5 failure");
            e.printStackTrace();
        }
        return encodedStr;
    }


}
