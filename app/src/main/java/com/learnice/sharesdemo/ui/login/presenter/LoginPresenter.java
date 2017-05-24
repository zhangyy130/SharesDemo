package com.learnice.sharesdemo.ui.login.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.learnice.sharesdemo.FunInterface.Idone;
import com.learnice.sharesdemo.app.App;
import com.learnice.sharesdemo.ui.login.contract.LoginContract;
import com.learnice.sharesdemo.ui.login.model.LoginModel;

/**
 * Created by Xuebin He on 2017/5/23.
 * e-mail:learnice.he@gmail.com.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.Model mModel;
    private LoginContract.View mView;
    private App app;

    public LoginPresenter(LoginContract.View view) {
        this.mView = view;
        mModel = new LoginModel();
        app = App.getInstance();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void toLogin(final String name, String pass) {
        mModel.toLogin(name, pass, new Idone() {

            @Override
            public void done(String status) {
                if (status.equals(LoginModel.OK)) {
                    mView.loginSuccess();
                    app.writeName(name);
                    app.writeLoginSuccess();
                }
                ToastUtils.showShortToast(status);
            }
        });
    }
}
