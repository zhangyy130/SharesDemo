package com.learnice.sharesdemo.ui.register.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.learnice.sharesdemo.fi.Idone;
import com.learnice.sharesdemo.ui.register.contract.RegisterContract;
import com.learnice.sharesdemo.ui.register.model.RegisterModel;

/**
 * Created by Xuebin He on 2017/5/24.
 * e-mail:learnice.he@gmail.com.
 */

public class RegisterPresenter implements RegisterContract.Presenter {

    private RegisterContract.View mView;
    private RegisterModel mModel;

    public RegisterPresenter(RegisterContract.View view) {
        this.mView = view;
        this.mModel = new RegisterModel();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void toRegister(String name, String pass1) {
        mModel.toRegister(name,pass1,new Idone(){
            @Override
            public void done(String status) {
                if (status.equals(RegisterModel.OK)){
                    mView.registerSuccess();
                }else {
                    ToastUtils.showShortToast(status);
                }
            }
        });
    }
}
