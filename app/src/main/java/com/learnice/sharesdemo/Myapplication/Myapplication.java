package com.learnice.sharesdemo.Myapplication;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Xuebin He on 2016/6/17.
 */
public class Myapplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
