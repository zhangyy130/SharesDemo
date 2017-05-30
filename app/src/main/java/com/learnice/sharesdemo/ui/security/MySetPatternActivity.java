package com.learnice.sharesdemo.ui.security;

import android.os.Bundle;

import com.learnice.base_library.baserx.RxManager;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.shareddata.AboutPatternLock;

import java.util.List;

import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;

public class MySetPatternActivity extends SetPatternActivity {

    RxManager rxManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.PatternView_Light);
        super.onCreate(savedInstanceState);
        rxManager = new RxManager();
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        super.onSetPattern(pattern);

        String patternSha1 = PatternUtils.patternToSha1String(pattern);
        AboutPatternLock.setPatternBool(this, true);
        AboutPatternLock.savePattern(this, patternSha1);

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
