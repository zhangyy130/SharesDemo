package com.learnice.sharesdemo.ui.main.presenter;

import android.content.Context;

import com.learnice.sharesdemo.ui.main.contract.MainContract;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class MainPresenter implements MainContract.Presenter {
    private Context mContext;

    public MainPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
