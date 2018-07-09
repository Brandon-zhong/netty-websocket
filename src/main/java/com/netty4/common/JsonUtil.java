package com.netty4.common;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by xuqi on 2017/10/17.
 */
public class JsonUtil {
    public static Gson gson = new GsonBuilder().create();

    public static String toJson(Object o) {
        return gson.toJson(o).toString();
    }

    public static Object fromJson(String message, Class clazz) {
        return gson.fromJson(message, clazz);
    }


    public static <T> T jsonToEntity(String jsonString, Class<T> clazz) {
        return gson.fromJson(jsonString, clazz);
    }

    public static <T> T objectToEntity(Object obj, Class<T> clazz) {
        return gson.fromJson(toJson(obj), clazz);
    }


}
