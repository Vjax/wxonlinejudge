package com.weijiax.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropUtil.class);

    /**
     * 读取properties文件
     * @param filename
     * @return
     */
    public static Properties loadProps(String filename){
        Properties props = null;
        InputStream is = null;
        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            if (is == null){
                throw new FileNotFoundException(filename+"file not found");
            }
            props = new Properties();
            props.load(is);
        }catch (IOException e){
            LOGGER.error("load properties file failure",e);
        }finally {
            if (is != null){
                try {
                    is.close();
                }catch (IOException e){
                    LOGGER.error("close input stream failure");
                }
            }
        }

        return props;
    }

    /**
     * 从properties读取字符串
     * @param props
     * @param key
     * @return
     */
    public static String getString(Properties props,String key){
        return getString(props,key,"");
    }

    public static String getString(Properties props,String key,String defaultValue){
        String value = defaultValue;
        if (props.containsKey(key)){
            value = props.getProperty(key);
        }
        return value;
    }

    /**
     * 从peoperties文件中读取整形值
     * @param props
     * @param key
     * @return
     */
    public static int getInt(Properties props,String key){
        return getInt(props,key,0);
    }

    public static int getInt(Properties props,String key,int defaultValue){
        int value = defaultValue;
        if(props.containsKey(key)){
            value = CastUtil.castInt(props.getProperty(key));
        }
        return value;
    }
}
