package com.learnice.sharesdemo.ui.details.contract;

import com.learnice.base_library.base.BaseModel;
import com.learnice.base_library.base.BasePresenter;
import com.learnice.base_library.base.BaseView;
import com.learnice.sharesdemo.fi.IDO;
import com.learnice.sharesdemo.fi.Idone;
import com.learnice.sharesdemo.bean.HKStockBean;
import com.learnice.sharesdemo.bean.StandStockBean;
import com.learnice.sharesdemo.bean.StockResponse;
import com.learnice.sharesdemo.bean.USStockBean;
import com.learnice.sharesdemo.bean.tb_say;
import com.learnice.sharesdemo.bean.tb_shares;

import java.util.List;

import rx.Observable;

/**
 * Created by Xuebin He on 2017/5/25.
 * e-mail:learnice.he@gmail.com.
 */

public interface DetailsContract {
    interface Model extends BaseModel {

        Observable<StockResponse<StandStockBean>> getSHData(String stockSymbol);

        Observable<StockResponse<StandStockBean>> getSZData(String stockSymbol);

        Observable<StockResponse<HKStockBean>> getHKData(String stockSymbol);

        Observable<StockResponse<USStockBean>> getUSData(String stockSymbol);

        void senSay(tb_say say, Idone idone);

        void getSayData(String stockType, String stockSymbol, IDO<List<tb_say>> ido, IDO<String> idos);

        void getSubscribeStatus(String userName, String stockType, String stockSymbol,IDO<Boolean> ido);

        void deleteStock(tb_shares shares, IDO<Boolean> ido);

        void addStock(tb_shares shares, IDO<Boolean> ido);
    }

    interface View extends BaseView {

        void setStockData(StandStockBean data);

        void sendSayResult(String status);

        void getSaydata(List<tb_say> tb_says);

        void getSubscribeStatus(Boolean aBoolean);

        void addSubscribeSuccess();

        void DeleteSubscribeSuccess();

        void sendSayResult(tb_say say);
    }

    interface Presenter extends BasePresenter {

        void getStockData(String type, String stock, String name);

        void sendSay(String content);

        void getSayData();

        void getSubscribeStatus();

        void changeSubscribeStatus(boolean checked);
    }
}
