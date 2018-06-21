package com.weijiax.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StreamUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    /**
     * 将字节流转化为字符串
     * @param inputStream
     * @return
     */
    public static String readStream(InputStream inputStream){
        StringBuilder builder = new StringBuilder();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream,"utf8"));
            while ((line = reader.readLine()) != null){
                builder.append(line);
                builder.append("\n");
            }
        }catch (IOException e){
            LOGGER.error("read inputstream error");
            e.printStackTrace();
        }finally {
            try {
                reader.close();
                inputStream.close();
            }catch (IOException e){
                LOGGER.error("close stream error");
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
