package com.learnice.base_library.app;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * Created by Xuebin He on 2017/5/15.
 * e-mail:learnice.he@gmail.com.
 */

public class BaseApplication extends Application {

    static Context _context;

    private static String PREF_NAME = "creativelocker.pref";

    @Override
    public void onCreate() {
        super.onCreate();

        _context = getApplicationContext();

    }

    public static synchronized BaseApplication context() {
        return (BaseApplication) _context;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static void apply(SharedPreferences.Editor editor) {
        /**
         * apply 异步提交
         * commit 同步提交
         * commit 会返回布尔值
         */
        editor.apply();

//        if (sIsAtLeastGB) {
//
//        } else {
//            editor.commit();
//        }
    }

    public static void set(String key, int value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        apply(editor);
    }

    public static void set(String key, boolean value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        apply(editor);
    }

    public static void set(String key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key, value);
        apply(editor);
    }

    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences() {
        SharedPreferences pre = context().getSharedPreferences(PREF_NAME,
                Context.MODE_MULTI_PROCESS);
        return pre;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static SharedPreferences getPreferences(String prefName) {
        return context().getSharedPreferences(prefName,
                Context.MODE_MULTI_PROCESS);
    }
}
