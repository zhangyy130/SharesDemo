package com.learnice.sharesdemo.ui.main.contract;

import com.learnice.base_library.base.BaseModel;
import com.learnice.base_library.base.BasePresenter;
import com.learnice.base_library.base.BaseView;
import com.learnice.sharesdemo.FunInterface.IDO;
import com.learnice.sharesdemo.FunInterface.ISubscribe;
import com.learnice.sharesdemo.bean.HKStockBean;
import com.learnice.sharesdemo.bean.StandStockBean;
import com.learnice.sharesdemo.bean.StockResponse;
import com.learnice.sharesdemo.bean.USStockBean;

import rx.Observable;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public interface SubscribeContract {
    interface Model extends BaseModel {

        void getSubscribeData(ISubscribe i, IDO<Integer> ido);

        Observable<StockResponse<StandStockBean>> getSHData(String stockSymbol);

        Observable<StockResponse<StandStockBean>> getSZData(String stockSymbol);

        Observable<StockResponse<HKStockBean>> getHKData(String stockSymbol);

        Observable<StockResponse<USStockBean>> getUSData(String stockSymbol);
    }

    interface View extends BaseView {

        void AddStockData(StandStockBean data);

        void LoadComplete();
    }

    interface Presenter extends BasePresenter {

        void getSubscribeData();
    }
}
