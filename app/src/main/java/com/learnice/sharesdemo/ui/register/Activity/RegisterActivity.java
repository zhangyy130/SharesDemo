package com.learnice.sharesdemo.ui.register.Activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.learnice.base_library.base.BaseActivity;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.ui.register.contract.RegisterContract;
import com.learnice.sharesdemo.ui.register.presenter.RegisterPresenter;
import butterknife.BindView;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func3;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View {


    @BindView(R.id.register_et_username)
    EditText registerEtUsername;
    @BindView(R.id.register_et_password)
    EditText registerEtPassword;
    @BindView(R.id.register_et_password2)
    EditText registerEtPassword2;
    @BindView(R.id.main_btn_register)
    Button mainBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        Observable<CharSequence> name = RxTextView.textChanges(registerEtUsername);
        final Observable<CharSequence> pass = RxTextView.textChanges(registerEtPassword);
        Observable<CharSequence> pass2 = RxTextView.textChanges(registerEtPassword2);
        Observable.combineLatest(name, pass, pass2, new Func3<CharSequence, CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
                boolean check = checkLength(charSequence) && checkLength(charSequence2) && checkLength(charSequence3);
                return check;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                RxView.enabled(mainBtnRegister).call(aBoolean);
            }
        });


        RxView.clicks(mainBtnRegister)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        String name = registerEtUsername.getText().toString().trim();
                        String pass1 = registerEtPassword.getText().toString().trim();
                        String pass2 = registerEtPassword2.getText().toString().trim();

                        if (chenkEquals(pass1, pass2)) {
                            mPresenter.toRegister(name,pass1);
                        }else {
                            ToastUtils.showShortToast("输入的两次密码不一致请重新输入");
                        }
                    }
                });
    }

    @Override
    public void initPresenter() {
        mPresenter = new RegisterPresenter(this);
    }

    private boolean checkLength(CharSequence charSequence) {
        return charSequence.length() > 0;
    }

    private boolean chenkEquals(String one, String two) {
        return one.equals(two);
    }

    @Override
    public void registerSuccess() {
        ToastUtils.showShortToast("注册成功请登录");
        finish();
    }
}
