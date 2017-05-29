package com.learnice.sharesdemo.ui.main.model;

import com.learnice.sharesdemo.api.Api;
import com.learnice.sharesdemo.api.ApiConstants;
import com.learnice.sharesdemo.api.HostType;
import com.learnice.sharesdemo.bean.HKStockListBean;
import com.learnice.sharesdemo.bean.SHStockListBean;
import com.learnice.sharesdemo.bean.SZStockListBean;
import com.learnice.sharesdemo.bean.StockListResponse;
import com.learnice.sharesdemo.bean.USStockListBean;
import com.learnice.sharesdemo.ui.main.contract.TrendContract;

import rx.Observable;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class TrendModel implements TrendContract.Model {
    @Override
    public Observable<StockListResponse<SHStockListBean>> getSHStockList(int page) {
        return Api.getDefault(HostType.JU_HE_STOCK)
                .getSHStockList(ApiConstants.STOCK_APPKEY, String.valueOf(page));
    }

    @Override
    public Observable<StockListResponse<SZStockListBean>> getSZStockList(int page) {
        return Api.getDefault(HostType.JU_HE_STOCK)
                .getSZStockList(ApiConstants.STOCK_APPKEY, String.valueOf(page));
    }

    @Override
    public Observable<StockListResponse<USStockListBean>> getUSStockList(int page) {
        return Api.getDefault(HostType.JU_HE_STOCK)
                .getUSStockList(ApiConstants.STOCK_APPKEY, String.valueOf(page));
    }

    @Override
    public Observable<StockListResponse<HKStockListBean>> getHKStockList(int page) {
        return Api.getDefault(HostType.JU_HE_STOCK)
                .getHKStockList(ApiConstants.STOCK_APPKEY, String.valueOf(page));
    }
}
