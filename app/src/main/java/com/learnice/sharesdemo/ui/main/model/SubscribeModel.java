package com.learnice.sharesdemo.ui.main.model;

import android.database.Cursor;

import com.learnice.sharesdemo.Database.SharesDataBase;
import com.learnice.sharesdemo.FunInterface.IDO;
import com.learnice.sharesdemo.FunInterface.ISubscribe;
import com.learnice.sharesdemo.api.Api;
import com.learnice.sharesdemo.api.ApiConstants;
import com.learnice.sharesdemo.api.HostType;
import com.learnice.sharesdemo.app.App;
import com.learnice.sharesdemo.bean.HKStockBean;
import com.learnice.sharesdemo.bean.StandStockBean;
import com.learnice.sharesdemo.bean.StockResponse;
import com.learnice.sharesdemo.bean.USStockBean;
import com.learnice.sharesdemo.ui.main.contract.SubscribeContract;

import rx.Observable;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class SubscribeModel implements SubscribeContract.Model {

    @Override
    public void getSubscribeData(ISubscribe i, IDO<Integer> ido) {
        Cursor cursor = App.getInstance().dbServices.select();
        int count = cursor.getCount();
        ido.done(count);
        if (count > 0) {
            while (cursor.moveToNext()) {
                String type = cursor.getString(cursor.getColumnIndex(SharesDataBase.SHARES_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(SharesDataBase.SHARES_NAME));
                i.get(type, name);
            }
        }
    }

    @Override
    public Observable<StockResponse<StandStockBean>> getSHData(String stockSymbol) {
        return Api.getDefault(HostType.JU_HE_STOCK)
                .getHSStock(stockSymbol, ApiConstants.STOCK_APPKEY);
    }

    @Override
    public Observable<StockResponse<StandStockBean>> getSZData(String stockSymbol) {
        return Api.getDefault(HostType.JU_HE_STOCK)
                .getHSStock(stockSymbol, ApiConstants.STOCK_APPKEY);
    }

    @Override
    public Observable<StockResponse<HKStockBean>> getHKData(String stockSymbol) {
        return Api.getDefault(HostType.JU_HE_STOCK)
                .getHKStock(stockSymbol, ApiConstants.STOCK_APPKEY);
    }

    @Override
    public Observable<StockResponse<USStockBean>> getUSData(String stockSymbol) {
        return Api.getDefault(HostType.JU_HE_STOCK)
                .getUSStock(stockSymbol, ApiConstants.STOCK_APPKEY);

    }
}
