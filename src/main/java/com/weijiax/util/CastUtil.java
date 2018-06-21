package com.weijiax.util;

public class CastUtil {

    public static int castInt(Object obj){
        return castInt(obj,-1);
    }

    public static int castInt(Object obj,int defaultValue){
        int intValue = defaultValue;
        if (obj != null){
            String strVlalue = castString(obj);
            if (StringUtil.isNotEmpty(strVlalue)){
                try {
                    intValue = Integer.parseInt(strVlalue);
                }catch (NumberFormatException e){
                    intValue = defaultValue;
                }
            }
        }
        return intValue;
    }

    public static boolean castBoolean(Object obj){
        return castBoolean(obj,false);
    }

    public static boolean castBoolean(Object obj,boolean defaultValue){
        boolean booleanValue = defaultValue;
        if (obj != null) {
            String strVlalue = castString(obj);
            if (StringUtil.isNotEmpty(strVlalue)) {
                try {
                    booleanValue = Boolean.parseBoolean(strVlalue);
                } catch (NumberFormatException e) {
                    booleanValue = defaultValue;
                }
            }
        }
        return booleanValue;
    }
    public static String castString(Object obj){
        return castString(obj,"");
    }

    public static String castString(Object obj,String defaultValue){
        return obj != null ? String.valueOf(obj) : defaultValue;
    }
}
