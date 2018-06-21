package com.weijiax.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class StringUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(StringUtil.class);

    public static boolean isEmpty(String str){
        if (str != null){
            str = str.trim();
        }
        return StringUtils.isEmpty(str);
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

    public static String encode(String str){
        String string = null;
        try {
            string = new String(str.getBytes("ISO-8859-1"),"UTF-8");
        }catch (UnsupportedEncodingException e){
            LOGGER.error("encode string failure");
            e.printStackTrace();
        }
        return string;
    }
}
