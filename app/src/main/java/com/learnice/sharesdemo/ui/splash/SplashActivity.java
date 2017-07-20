package com.learnice.sharesdemo.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.learnice.sharesdemo.app.App;
import com.learnice.sharesdemo.shareddata.AboutPatternLock;
import com.learnice.sharesdemo.ui.login.Activity.LoginActivity;
import com.learnice.sharesdemo.ui.main.activity.MainActivity;
import com.learnice.sharesdemo.ui.security.MyConfirmPatternActivity;

/**
 * Created by Learnice on 2017/7/20.
 */

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                openActivity(LoginActivity.class);
            }
        }, 500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void openActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }
}
