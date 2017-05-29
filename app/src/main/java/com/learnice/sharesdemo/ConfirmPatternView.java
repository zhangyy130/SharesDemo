package com.learnice.sharesdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Button;

import com.learnice.sharesdemo.shareddata.AboutFirst;
import com.learnice.sharesdemo.shareddata.AboutPatternLock;
import com.learnice.sharesdemo.ui.login.Activity.LoginActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.zhanghai.android.patternlock.ConfirmPatternActivity;
import me.zhanghai.android.patternlock.PatternUtils;
import me.zhanghai.android.patternlock.PatternView;

public class ConfirmPatternView extends ConfirmPatternActivity {

    @BindView(R.id.confirm_pattern_view)
    PatternView confirmPatternView;
    String have = null;
    @BindView(R.id.cancel_confirm)
    Button cancelConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AboutFirst.readSave(this)){
            startActivity(new Intent(this,GuideActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_confirm_pattern_view);

        ButterKnife.bind(this);
        have = getIntent().getStringExtra("have");
        if (have == null) {


            if (AboutPatternLock.readPatternBool(this)) {

                confirmPatternView.setOnPatternListener(new PatternView.OnPatternListener() {
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
                        Log.e("confirmPattern", PatternUtils.patternToSha1String(pattern));
                        Log.e("aboutPattern", AboutPatternLock.readPattern(ConfirmPatternView.this));
                        if (PatternUtils.patternToSha1String(pattern).equals(AboutPatternLock.readPattern(ConfirmPatternView.this))) {
                            Intent intent = new Intent(ConfirmPatternView.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Snackbar.make(confirmPatternView, "密码错误", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Intent intent = new Intent(ConfirmPatternView.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            confirmPatternView.setOnPatternListener(new PatternView.OnPatternListener() {
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
                    if (AboutPatternLock.readPattern(ConfirmPatternView.this).equals(PatternUtils.patternToSha1String(pattern))) {
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        Snackbar.make(confirmPatternView, "密码错误", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @OnClick(R.id.cancel_confirm)
    public void onClick() {
        finish();
    }
}
