package com.weijiax.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

    private static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    public static JSONObject getJsonObject(String json){
        return JSON.parseObject(json);
    }

    public static JSONArray getJsonArray(String json){
        return JSON.parseArray(json);
    }

    public static <T> T getJavaBean(String json,Class<T> type){
        return JSON.parseObject(json,type);
    }

    public static String toJson(Object object){
        return JSON.toJSONString(object);
    }

    public static String toJson(Object object,String name){
        String json = "{\""+name+"\":"+JSON.toJSONString(object)+"}";
        return json;
    }
}
