package com.one.hotchpotch.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.one.hotchpotch.MyApp;

/**
 * 对SharePreference的封装
 *
 * @author Kevin
 */
public class PrefUtils {
    private static SharedPreferences sp = MyApp.getInstance().getSharedPreferences("config", Context.MODE_PRIVATE);

    public static void putBoolean(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public static void putString(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    public static String getString(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public static void putInt(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    public static int getInt(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static void remove(String key) {
        sp.edit().remove(key).commit();
    }

    public static void clear() {
        sp.edit().clear().commit();
    }
}
