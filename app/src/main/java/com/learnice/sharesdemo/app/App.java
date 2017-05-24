package com.learnice.sharesdemo.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.blankj.utilcode.util.Utils;
import com.learnice.base_library.app.BaseApplication;

import cn.bmob.v3.Bmob;

/**
 * Created by Xuebin He on 2017/5/15.
 * e-mail:learnice.he@gmail.com.
 */

public class App extends BaseApplication {

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Bmob.initialize(this, "46af27f1da81ec401f1b58fae9999642");
        Utils.init(this);
    }

    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static App getInstance() {
        return instance;
    }

    public void writeName(String name) {
        SharedPreferences.Editor editor = this.getSharedPreferences("user", Context.MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.commit();
    }

    public String readName() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "未登录");
    }

    public void writeLoginSuccess() {
        SharedPreferences.Editor editor = this.getSharedPreferences("login", Context.MODE_PRIVATE).edit();
        editor.putBoolean("login", true);
        editor.commit();
    }

    public boolean readLoginSuccess() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("login", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("login", false);
    }

    public void clearLoginSuccess() {
        SharedPreferences.Editor editor = this.getSharedPreferences("login", Context.MODE_PRIVATE).edit();
        editor.putBoolean("login", false);
        editor.commit();
    }


}
