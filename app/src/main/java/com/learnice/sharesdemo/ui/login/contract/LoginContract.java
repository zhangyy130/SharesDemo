package com.learnice.sharesdemo.ui.login.contract;

import com.learnice.base_library.base.BaseModel;
import com.learnice.base_library.base.BasePresenter;
import com.learnice.base_library.base.BaseView;
import com.learnice.sharesdemo.fi.IDO;
import com.learnice.sharesdemo.fi.Idone;

/**
 * Created by Xuebin He on 2017/5/23.
 * e-mail:learnice.he@gmail.com.
 */

public interface LoginContract {
    interface View extends BaseView {

        void loginSuccess();

        void setGoneProgress();
    }

    interface Model extends BaseModel {

        void toLogin(String name, String pass, Idone iDone);

        void syncData(String name, IDO<Boolean> ido);
    }

    interface Presenter extends BasePresenter {

        void toLogin(String name, String pass);
    }
}
