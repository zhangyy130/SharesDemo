package com.learnice.sharesdemo.shareddata;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Xuebin He on 2016/6/28.
 */
public class AboutFirst {
    public static void firstSave(Context context){
        SharedPreferences.Editor editor=context.getSharedPreferences("first",Context.MODE_PRIVATE).edit();
        editor.putBoolean("first",false);
        editor.commit();
    }
    public static boolean readSave(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences("first",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("first",true);
    }
}
