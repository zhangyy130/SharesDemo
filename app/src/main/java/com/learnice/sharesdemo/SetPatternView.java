package com.learnice.sharesdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.learnice.sharesdemo.shareddata.AboutPatternLock;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;
import me.zhanghai.android.patternlock.SetPatternActivity;

public class SetPatternView extends SetPatternActivity {

    @BindView(R.id.set_pattern_view)
    PatternView setPatternView;
    @BindView(R.id.set_pattern_cancel)
    Button setPatternCancel;
    @BindView(R.id.set_pattern_cancel_right)
    Button setPatternCancelRight;
    int status = 0;
    String one;
    String two;
    @BindView(R.id.set_pattern_toolbar)
    Toolbar setPatternToolbar;
    @BindView(R.id.pattern_top_text)
    TextView patternTopText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pattern_view);
        ButterKnife.bind(this);
        setSupportActionBar(setPatternToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        actionBar.setTitle("手势密码");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setPatternView.setOnPatternListener(new PatternView.OnPatternListener() {
            @Override
            public void onPatternStart() {

            }

            @Override
            public void onPatternCleared() {

            }

            @Override
            public void onPatternCellAdded(List<PatternView.Cell> pattern) {

            }

            @Override
            public void onPatternDetected(List<PatternView.Cell> pattern) {
                if (status == 0) {
                    one = PatternUtils.patternToSha1String(pattern);
                } else if (status == 1) {
                    two = PatternUtils.patternToSha1String(pattern);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSetPattern(List<PatternView.Cell> pattern) {
        super.onSetPattern(pattern);
    }

    @OnClick({R.id.set_pattern_cancel, R.id.set_pattern_cancel_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.set_pattern_cancel:
                finish();
                break;
            case R.id.set_pattern_cancel_right:
                if (status == 0) {
                    status = 1;
                    setPatternView.clearPattern();
                    patternTopText.setText("再次绘制解锁图案");
                } else if (status == 1) {
                    if (one.equals(two)) {
                        AboutPatternLock.setPatternBool(this, true);
                        AboutPatternLock.savePattern(this, two);
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Snackbar.make(setPatternView, "输入的两次手势不一样", Snackbar.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

}
