package com.learnice.sharesdemo.ui.changepass.contract;

import com.learnice.base_library.base.BaseModel;
import com.learnice.base_library.base.BasePresenter;
import com.learnice.base_library.base.BaseView;
import com.learnice.sharesdemo.fi.IDO;

/**
 * Created by Xuebin He on 2017/5/30.
 * e-mail:learnice.he@gmail.com.
 */

public interface ChangePassContract {
    interface Model extends BaseModel {

        void changePassWord(String userName, String s, String s1, IDO<String> ido);
    }

    interface View extends BaseView {

        void ChangePassSuccess();

        void setGoneProgress();
    }

    interface Presenter extends BasePresenter {

        void changePassWord(String s, String s1, String s2);
    }
}
