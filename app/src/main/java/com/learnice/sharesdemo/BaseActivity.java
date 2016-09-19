package com.learnice.sharesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Xuebin He on 2016/9/19.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_left_right,R.anim.anim_out_left_right);
    }
}
