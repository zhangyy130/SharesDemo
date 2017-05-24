package com.learnice.sharesdemo.ui.main.presenter;

import com.learnice.base_library.baserx.RxManager;
import com.learnice.sharesdemo.bean.NewsListResponse;
import com.learnice.sharesdemo.bean.NewsBean;
import com.learnice.sharesdemo.ui.main.contract.HomeContract;
import com.learnice.sharesdemo.ui.main.model.HomeModel;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class HomePresenter implements HomeContract.Presenter {

    HomeContract.Model mModel;
    RxManager rxManager;
    HomeContract.View mView;

    public HomePresenter(HomeContract.View view) {
        this.mModel = new HomeModel();
        rxManager = new RxManager();
        this.mView = view;
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        rxManager.clear();
    }

    @Override
    public void loadNews() {
        rxManager.add(mModel.loadNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsListResponse<NewsBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NewsListResponse<NewsBean> newsBeanBaseResponse) {
                        if (newsBeanBaseResponse.getReason().equals("成功的返回")){
                            mView.setNewsData(newsBeanBaseResponse.getResult().getData());
                        }
                    }
                }));
    }
}
