package com.example.baseapplication.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.baseapplication.TemplateApplication;

/*
 * SharedPreferences的一个工具类，调用setParam就能保存String, Integer, Boolean, Float, Long类型的参数
 * 同样调用getParam就能获取到保存在手机里面的数据

 * 保存数据方法：
 * SharedPreferencesUtils.setParam("String", "xiaanming");
 * SharedPreferencesUtils.setParam("int", 10);
 * SharedPreferencesUtils.setParam("boolean", true);
 * SharedPreferencesUtils.setParam("long", 100L);
 * SharedPreferencesUtils.setParam("float", 1.1f);

 * 获取数据方法：
 * SharedPreferencesUtils.getParam("String", "");
 * SharedPreferencesUtils.getParam("int", 0);
 * SharedPreferencesUtils.getParam("boolean", false);
 * SharedPreferencesUtils.getParam("long", 0L);
 * SharedPreferencesUtils.getParam("float", 0.0f);
 */

public class SharedPreferencesUtil {

    private static final String FILE_NAME = "share_date";

    /**
     * 保存数据的方法，需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void setParam(String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = TemplateApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }
        //异步存储，无返回值
        editor.apply();
        //同步存储，有返回值
        //editor.commit();
    }

    /**
     * 得到数据的方法，根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object getParam(String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = TemplateApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }
}


