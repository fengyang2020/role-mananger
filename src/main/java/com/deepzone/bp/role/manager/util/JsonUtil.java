package com.deepzone.bp.role.manager.util;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author yangfeng
 * created 2024/8/8 15:06 v1.0
 */
public class JsonUtil {

    public static <T> T parseObject(String text, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(text, type);
    }

    public static <T> List<T> parseList(String text, Class<T> clazz) {
        Gson gson = new Gson();
        Type type = new ListParameterizedType(clazz);
        return gson.fromJson(text, type);
    }

    public static String toJSONString(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    private static class ListParameterizedType implements ParameterizedType {

        Class clazz;

        public ListParameterizedType(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
