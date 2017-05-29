package com.learnice.sharesdemo.ui.main.contract;

import com.learnice.base_library.base.BaseModel;
import com.learnice.base_library.base.BasePresenter;
import com.learnice.base_library.base.BaseView;
import com.learnice.sharesdemo.bean.StockType;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public interface MainContract {
    interface Model extends BaseModel {

    }

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {

        void selectStock(StockType stock);
    }
}
