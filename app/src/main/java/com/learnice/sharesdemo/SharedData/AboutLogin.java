package com.learnice.sharesdemo.SharedData;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Xuebin He on 2016/6/20.
 */
public class AboutLogin {
    Context context;

    public AboutLogin(Context context) {
        this.context = context;
    }

    public  void writeLoginSuccess(){
        SharedPreferences.Editor editor=context.getSharedPreferences("login",Context.MODE_PRIVATE).edit();
        editor.putBoolean("login",true);
        editor.commit();
    }
    public boolean readLoginSuccess(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("login",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("login",false);
    }
    public void clearLoginSuccess(){
        SharedPreferences.Editor editor=context.getSharedPreferences("login",Context.MODE_PRIVATE).edit();
        editor.putBoolean("login",false);
        editor.commit();
    }

}
