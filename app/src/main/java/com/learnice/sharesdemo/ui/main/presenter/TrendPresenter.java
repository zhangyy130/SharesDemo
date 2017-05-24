package com.learnice.sharesdemo.ui.main.presenter;

import com.learnice.sharesdemo.ui.main.contract.TrendContract;
import com.learnice.sharesdemo.ui.main.model.TrendModel;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class TrendPresenter implements TrendContract.Presenter {
    private TrendContract.View mView;
    private TrendModel mModel;

    public TrendPresenter(TrendContract.View view) {
        this.mView = view;
        mModel = new TrendModel();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
