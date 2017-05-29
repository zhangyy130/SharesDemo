package com.learnice.sharesdemo.shareddata;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Xuebin He on 2016/6/27.
 */
public class AboutPatternLock {
    public static final String PATTERN="pattern";
    public static final String PATTERN_BOOL="pattern_bool";
    public static void savePattern(Context context,String pattern){
        SharedPreferences.Editor editor=context.getSharedPreferences(PATTERN,Context.MODE_PRIVATE).edit();
        editor.putString(PATTERN,pattern);
        editor.commit();
    }
    public static String readPattern(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PATTERN,Context.MODE_PRIVATE);
        return sharedPreferences.getString(PATTERN,"0");
    }
    public static void setPatternBool(Context context,boolean bool){
        SharedPreferences.Editor editor=context.getSharedPreferences(PATTERN_BOOL,Context.MODE_PRIVATE).edit();
        editor.putBoolean(PATTERN_BOOL,bool);
        editor.commit();
    }
    public static boolean readPatternBool(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(PATTERN_BOOL,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(PATTERN_BOOL,false);
    }
}
