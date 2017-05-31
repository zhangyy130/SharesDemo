package com.learnice.sharesdemo.ui.changepass.presenter;

import com.blankj.utilcode.util.ToastUtils;
import com.learnice.sharesdemo.app.App;
import com.learnice.sharesdemo.fi.IDO;
import com.learnice.sharesdemo.ui.changepass.Activity.ChangePassActivity;
import com.learnice.sharesdemo.ui.changepass.contract.ChangePassContract;
import com.learnice.sharesdemo.ui.changepass.model.ChangePassModel;

/**
 * Created by Xuebin He on 2017/5/30.
 * e-mail:learnice.he@gmail.com.
 */

public class ChangePassPresenter implements ChangePassContract.Presenter {

    private ChangePassContract.View mView;
    private ChangePassModel mModel;
    private String userName;

    public ChangePassPresenter(ChangePassContract.View view) {
        this.mView = view;
        mModel = new ChangePassModel();
        userName = App.getInstance().readName();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void changePassWord(String s, String s1, String s2) {
        if (s1.equals(s2)) {
            mModel.changePassWord(userName, s, s1,new IDO<String>(){

                @Override
                public void done(String s) {
                    ToastUtils.showShortToast(s);
                    if (s.equals(ChangePassModel.OK)){
                        mView.ChangePassSuccess();
                    }else {
                        mView.setGoneProgress();
                    }

                }
            });
        } else {
            ToastUtils.showShortToast("输入的两次密码不同");
            mView.setGoneProgress();
        }
    }
}
