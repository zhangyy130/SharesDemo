package com.learnice.sharesdemo.ui.main.presenter;

import android.content.Context;

import com.learnice.base_library.baserx.RxManager;
import com.learnice.sharesdemo.bean.StockType;
import com.learnice.sharesdemo.ui.main.contract.MainContract;
import com.learnice.sharesdemo.widget.adapter.MViewPagerAdapter;

import rx.functions.Action1;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class MainPresenter implements MainContract.Presenter {

    private RxManager rxManager;
    private MainContract.View mView;

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        rxManager = new RxManager();
    }

    @Override
    public void subscribe() {
        rxManager.on("updateLockStatus", new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mView.updateLockStatus();
            }
        });
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void selectStock(StockType stock) {
        rxManager.post("StockType", stock);
    }
}
