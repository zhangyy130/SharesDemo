package com.learnice.sharesdemo.ui.main.contract;

import com.learnice.base_library.base.BaseModel;
import com.learnice.base_library.base.BasePresenter;
import com.learnice.base_library.base.BaseView;
import com.learnice.sharesdemo.bean.HKStockListBean;
import com.learnice.sharesdemo.bean.SHStockListBean;
import com.learnice.sharesdemo.bean.SZStockListBean;
import com.learnice.sharesdemo.bean.StockListResponse;
import com.learnice.sharesdemo.bean.USStockListBean;

import java.util.List;

import rx.Observable;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public interface TrendContract {
    interface Model extends BaseModel {

        Observable<StockListResponse<SHStockListBean>> getSHStockList(int page);

        Observable<StockListResponse<SZStockListBean>> getSZStockList(int page);

        Observable<StockListResponse<USStockListBean>> getUSStockList(int page);

        Observable<StockListResponse<HKStockListBean>> getHKStockList(int page);
    }

    interface View extends BaseView {

        void getStockList(List<SHStockListBean> data);

        void flushStockList();
    }

    interface Presenter extends BasePresenter {
        void getStockList();

        void initPage();

    }
}
