package com.learnice.sharesdemo.ui.main.presenter;

import android.content.Context;

import com.learnice.base_library.baserx.RxManager;
import com.learnice.sharesdemo.bean.StockType;
import com.learnice.sharesdemo.ui.main.contract.MainContract;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class MainPresenter implements MainContract.Presenter {

    private RxManager rxManager;
    private Context mContext;

    public MainPresenter(Context mContext) {
        this.mContext = mContext;
        rxManager = new RxManager();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void selectStock(StockType stock) {
        rxManager.post("StockType",stock);
    }
}
