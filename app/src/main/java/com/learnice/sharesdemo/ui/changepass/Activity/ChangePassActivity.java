package com.learnice.sharesdemo.ui.changepass.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.learnice.base_library.base.BaseActivity;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.ui.changepass.contract.ChangePassContract;
import com.learnice.sharesdemo.ui.changepass.presenter.ChangePassPresenter;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func3;


public class ChangePassActivity extends BaseActivity<ChangePassPresenter> implements ChangePassContract.View {


    @BindView(R.id.change_pass_toolbar)
    Toolbar changePassToolbar;
    @BindView(R.id.change_pass_et_current_pass)
    EditText changePassEtCurrentPass;
    @BindView(R.id.change_pass_et_password)
    EditText changePassEtPassword;
    @BindView(R.id.change_pass_et_password2)
    EditText changePassEtPassword2;
    @BindView(R.id.main_btn_change_pass)
    Button mainBtnChangePass;
    @BindView(R.id.change_pass_progress)
    ProgressBar changePassProgress;
    @BindView(R.id.change_pass_progress_layout)
    RelativeLayout changePassProgressLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_pass;
    }

    @Override
    public void initView() {
        setSupportActionBar(changePassToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("修改密码");
        actionBar.setHomeAsUpIndicator(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Observable.combineLatest(RxTextView.textChanges(changePassEtCurrentPass),
                RxTextView.textChanges(changePassEtPassword),
                RxTextView.textChanges(changePassEtPassword2), new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
                        return charSequence.length() > 0 && charSequence2.length() > 0 && charSequence3.length() > 0;
                    }
                }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                RxView.enabled(mainBtnChangePass)
                        .call(aBoolean);
            }
        });
        RxView.clicks(mainBtnChangePass)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        changePassProgressLayout.setVisibility(View.VISIBLE);
                        mPresenter.changePassWord(changePassEtCurrentPass.getText().toString(),
                                changePassEtPassword.getText().toString(),
                                changePassEtPassword2.getText().toString());
                    }
                });
    }

    @Override
    public void initPresenter() {
        mPresenter = new ChangePassPresenter(this);
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
    public void ChangePassSuccess() {
        finish();
    }

    @Override
    public void setGoneProgress() {
        changePassProgressLayout.setVisibility(View.GONE);
    }

}
