package com.learnice.sharesdemo.ui.security;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.learnice.base_library.baserx.RxManager;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.shareddata.AboutPatternLock;
import com.learnice.sharesdemo.ui.main.activity.MainActivity;


import java.util.List;

import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

public class MyConfirmPatternActivity extends ConfirmPatternActivity {

    RxManager rxManager;
    private boolean mIsStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.PatternView);
        super.onCreate(savedInstanceState);
        rxManager = new RxManager();
        mRightButton.setVisibility(View.GONE);
        mIsStart = getIntent().getBooleanExtra("isStart", false);
    }

    @Override
    protected boolean isStealthModeEnabled() {
        return super.isStealthModeEnabled();
    }

    @Override
    protected void onForgotPassword() {
        super.onForgotPassword();
    }

    @Override
    protected boolean isPatternCorrect(List<PatternView.Cell> pattern) {
        String sha1 = AboutPatternLock.readPattern(this);
        boolean isTrue = TextUtils.equals(PatternUtils.patternToSha1String(pattern), sha1);
        if (isTrue && !mIsStart) {
            AboutPatternLock.setPatternBool(this, false);
        } else if (isTrue && mIsStart) {
            startActivity(new Intent(this, MainActivity.class));
        }
        return isTrue;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        if (!mIsStart) {
            rxManager.post("updateLockStatus", null);
        }
    }
}
