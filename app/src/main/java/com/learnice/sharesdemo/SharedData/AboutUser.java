package com.learnice.sharesdemo.SharedData;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Xuebin He on 2016/6/20.
 */
public class AboutUser {
    Context context;

    public AboutUser(Context context) {
        this.context = context;
    }
    public void writeName(String name){
        SharedPreferences.Editor editor=context.getSharedPreferences("user",Context.MODE_PRIVATE).edit();
        editor.putString("name",name);
        editor.commit();
    }
    public String readName(){
        SharedPreferences sharedPreferences=context.getSharedPreferences("user",Context.MODE_PRIVATE);
        return sharedPreferences.getString("name","未登录");
    }
}
