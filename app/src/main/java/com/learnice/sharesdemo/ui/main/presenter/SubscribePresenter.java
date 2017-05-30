package com.learnice.sharesdemo.ui.main.presenter;

import android.util.Log;

import com.learnice.base_library.baserx.RxManager;
import com.learnice.sharesdemo.fi.IDO;
import com.learnice.sharesdemo.fi.ISubscribe;
import com.learnice.sharesdemo.bean.HKStockBean;
import com.learnice.sharesdemo.bean.StandStockBean;
import com.learnice.sharesdemo.bean.StockResponse;
import com.learnice.sharesdemo.bean.USStockBean;
import com.learnice.sharesdemo.ui.main.contract.SubscribeContract;
import com.learnice.sharesdemo.ui.main.model.SubscribeModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class SubscribePresenter implements SubscribeContract.Presenter {
    RxManager rxManager;
    SubscribeModel mModel;
    SubscribeContract.View mView;
    int subscribeCount;

    public SubscribePresenter(SubscribeContract.View mView) {
        this.mView = mView;
        mModel = new SubscribeModel();
        rxManager = new RxManager();
    }

    @Override
    public void subscribe() {
        rxManager.on("subscribeUpdate", new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                mView.clearData();
                getSubscribeData();
            }
        });
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void getSubscribeData() {
        mView.subscribeIsNull();
        mModel.getSubscribeData(new ISubscribe() {
            @Override
            public void get(String type, String symbol) {
                Observable observable = null;
                switch (type) {
                    case "sh":
                        observable = mModel.getSHData(symbol).map(new Func1<StockResponse<StandStockBean>, StockResponse<StandStockBean>>() {

                            @Override
                            public StockResponse<StandStockBean> call(StockResponse<StandStockBean> standStockBeanStockResponse) {
                                standStockBeanStockResponse.getResult().get(0).getData().setType("sh");
                                return standStockBeanStockResponse;
                            }
                        });
                        break;
                    case "sz":
                        observable = mModel.getSZData(symbol).map(new Func1<StockResponse<StandStockBean>, StockResponse<StandStockBean>>() {

                            @Override
                            public StockResponse<StandStockBean> call(StockResponse<StandStockBean> standStockBeanStockResponse) {
                                standStockBeanStockResponse.getResult().get(0).getData().setType("sz");
                                return standStockBeanStockResponse;
                            }
                        });
                        break;
                    case "hk":
                        observable = mModel.getHKData(symbol)
                                .map(new Func1<StockResponse<HKStockBean>, StockResponse<StandStockBean>>() {
                                    @Override
                                    public StockResponse<StandStockBean> call(StockResponse<HKStockBean> hkStockBeanStockResponse) {
                                        StockResponse<StandStockBean> response = new StockResponse<StandStockBean>();
                                        response.setError_code(hkStockBeanStockResponse.getError_code());
                                        response.setReason(hkStockBeanStockResponse.getReason());
                                        response.setResultcode(response.getResultcode());
                                        //设置返回的bean
                                        StandStockBean bean = new StandStockBean();
                                        HKStockBean hkBean = hkStockBeanStockResponse.getResult().get(0).getData();

                                        bean.setType("hk");
                                        bean.setName(hkBean.getName());
                                        String gid = hkBean.getGid();
                                        gid = gid.substring(2, gid.length());
                                        bean.setGid(gid);
                                        bean.setNowPri(hkBean.getLastestpri());
                                        bean.setIncrease(hkBean.getUppic());
                                        bean.setIncrePer(hkBean.getLimit());
                                        bean.setTraNumber(hkBean.getTraNumber());
                                        bean.setTodayMax(hkBean.getMaxpri());
                                        bean.setTodayMin(hkBean.getMinpri());
                                        bean.setDate(hkBean.getDate());
                                        bean.setTodayStartPri(hkBean.getOpenpri());
                                        bean.setYestodEndPri(hkBean.getFormpri());


                                        List<StockResponse.ResultBean<StandStockBean>> list = new ArrayList<>();
                                        StockResponse.ResultBean<StandStockBean> result = new StockResponse.ResultBean<>();
                                        result.setData(bean);


                                        list.add(result);
                                        response.setResult(list);
                                        return response;
                                    }
                                });
                        break;
                    case "us":
                        observable = mModel.getUSData(symbol)
                                .map(new Func1<StockResponse<USStockBean>, StockResponse<StandStockBean>>() {
                                    @Override
                                    public StockResponse<StandStockBean> call(StockResponse<USStockBean> usStockBeanStockResponse) {
                                        StockResponse<StandStockBean> response = new StockResponse<StandStockBean>();
                                        response.setError_code(usStockBeanStockResponse.getError_code());
                                        response.setReason(usStockBeanStockResponse.getReason());
                                        response.setResultcode(response.getResultcode());
                                        //设置返回的bean
                                        StandStockBean bean = new StandStockBean();
                                        USStockBean usBean = usStockBeanStockResponse.getResult().get(0).getData();


                                        bean.setType("us");
                                        bean.setName(usBean.getName());
                                        bean.setGid(usBean.getGid());
                                        bean.setNowPri(usBean.getLastestpri());
                                        bean.setIncrease(usBean.getUppic());
                                        bean.setIncrePer(usBean.getLimit());
                                        bean.setTraNumber(usBean.getTraAmount());
                                        bean.setTodayMax(usBean.getMaxpri());
                                        bean.setTodayMin(usBean.getMinpri());
                                        bean.setDate(usBean.getChtime());
                                        bean.setTodayStartPri(usBean.getOpenpri());
                                        bean.setYestodEndPri(usBean.getFormpri());

                                        List<StockResponse.ResultBean<StandStockBean>> list = new ArrayList<>();
                                        StockResponse.ResultBean<StandStockBean> result = new StockResponse.ResultBean<>();
                                        result.setData(bean);


                                        list.add(result);
                                        response.setResult(list);

                                        return response;
                                    }
                                });
                        break;
                }
                rxManager.add(observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<StockResponse<StandStockBean>>() {
                            @Override
                            public void onCompleted() {
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Log.d("------------", throwable.getMessage());
                            }

                            @Override
                            public void onNext(StockResponse<StandStockBean> standStockBeanStockResponse) {
                                if (standStockBeanStockResponse.getError_code() == 0) {
                                    mView.AddStockData(standStockBeanStockResponse.getResult().get(0).getData());
                                    --subscribeCount;
                                    if (subscribeCount == 0) {
                                        mView.LoadComplete();
                                    }
                                }
                            }
                        }));

            }
        }, new IDO<Integer>() {

            @Override
            public void done(Integer i) {
                subscribeCount = i;
            }
        });
    }

}
