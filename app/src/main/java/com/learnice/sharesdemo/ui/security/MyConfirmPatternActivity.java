package com.learnice.sharesdemo.ui.security;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.learnice.base_library.baserx.RxManager;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.shareddata.AboutPatternLock;


import java.util.List;

import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

public class MyConfirmPatternActivity extends ConfirmPatternActivity {

    RxManager rxManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.PatternView);
        super.onCreate(savedInstanceState);
        rxManager = new RxManager();
        mRightButton.setVisibility(View.GONE);
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
        if (isTrue) {
            AboutPatternLock.setPatternBool(this, false);
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
        rxManager.post("updateLockStatus", null);
    }
}
