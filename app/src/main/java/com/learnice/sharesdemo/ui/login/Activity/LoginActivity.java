package com.learnice.sharesdemo.ui.login.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.learnice.base_library.base.BaseActivity;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.app.App;
import com.learnice.sharesdemo.ui.register.Activity.RegisterActivity;
import com.learnice.sharesdemo.ui.login.contract.LoginContract;
import com.learnice.sharesdemo.ui.login.presenter.LoginPresenter;
import com.learnice.sharesdemo.ui.main.activity.MainActivity;
import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func2;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {


    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.main_btn_login)
    Button mainBtnLogin;
    @BindView(R.id.login_register)
    TextView loginRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (App.getInstance().readLoginSuccess()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        Observable.combineLatest(
                RxTextView.textChanges(etUsername),
                RxTextView.textChanges(etPassword),
                new Func2<CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence, CharSequence charSequence2) {
                        boolean check = charSequence.length() > 0 && charSequence2.length() > 0;
                        return check;
                    }
                }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                RxView.enabled(mainBtnLogin).call(aBoolean);
            }
        });

        RxView.clicks(mainBtnLogin)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        String name = etUsername.getText().toString().trim();
                        String pass = etPassword.getText().toString().trim();
                        mPresenter.toLogin(name, pass);
                    }
                });
        RxView.clicks(loginRegister)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        openActivity(RegisterActivity.class);
                    }
                });
    }

    @Override
    public void initPresenter() {
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void loginSuccess() {
        openActivity(MainActivity.class);
        finish();
    }
}

