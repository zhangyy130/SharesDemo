package com.learnice.sharesdemo.ui.register.contract;

import com.learnice.base_library.base.BaseModel;
import com.learnice.base_library.base.BasePresenter;
import com.learnice.base_library.base.BaseView;
import com.learnice.sharesdemo.FunInterface.Idone;

/**
 * Created by Xuebin He on 2017/5/24.
 * e-mail:learnice.he@gmail.com.
 */

public interface RegisterContract {
    interface Model extends BaseModel {

        void toRegister(String name, String pass1, Idone idone);
    }

    interface View extends BaseView {

        void registerSuccess();
    }

    interface Presenter extends BasePresenter {

        void toRegister(String name, String pass1);
    }
}
