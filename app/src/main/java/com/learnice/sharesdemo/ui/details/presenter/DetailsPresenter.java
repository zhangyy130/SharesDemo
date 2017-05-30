package com.learnice.sharesdemo.ui.details.presenter;

import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.learnice.base_library.baserx.RxManager;
import com.learnice.sharesdemo.fi.IDO;
import com.learnice.sharesdemo.fi.Idone;
import com.learnice.sharesdemo.app.App;
import com.learnice.sharesdemo.bean.HKStockBean;
import com.learnice.sharesdemo.bean.StandStockBean;
import com.learnice.sharesdemo.bean.StockResponse;
import com.learnice.sharesdemo.bean.USStockBean;
import com.learnice.sharesdemo.bean.tb_say;
import com.learnice.sharesdemo.bean.tb_shares;
import com.learnice.sharesdemo.ui.details.contract.DetailsContract;
import com.learnice.sharesdemo.ui.details.model.DetailsModel;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Xuebin He on 2017/5/25.
 * e-mail:learnice.he@gmail.com.
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    DetailsContract.View mView;
    DetailsModel mModel;
    String stockType;
    String stockSymbol;
    String stockName;
    String userName;
    private RxManager rxManager;
    App app;

    public DetailsPresenter(DetailsContract.View mView) {
        this.mView = mView;
        mModel = new DetailsModel();
        rxManager = new RxManager();
        app = App.getInstance();
        this.userName = app.readName();
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    public void getStockData(String type, String stock, String name) {
        this.stockType = type;
        this.stockSymbol = stock;
        this.stockName = name;
        requestData();
    }

    @Override
    public void sendSay(String content) {
        final tb_say say = new tb_say();
        say.setUserName(userName);
        say.setSharesType(stockType);
        say.setSharesNumber(stockSymbol);
        say.setSharesName(stockName);
        say.setContent(content);
        mModel.senSay(say, new Idone() {

            @Override
            public void done(String status) {
                ToastUtils.showShortToast(status);
                mView.sendSayResult(say);
            }
        });
    }

    @Override
    public void getSayData() {
        mModel.getSayData(stockType, stockSymbol, new IDO<List<tb_say>>() {

            @Override
            public void done(List<tb_say> tb_says) {
                mView.getSaydata(tb_says);
            }
        }, new IDO<String>() {

            @Override
            public void done(String s) {

            }
        });
    }

    @Override
    public void getSubscribeStatus() {
        mModel.getSubscribeStatus(userName, stockType, stockSymbol, new IDO<Boolean>() {

            @Override
            public void done(Boolean aBoolean) {
                mView.getSubscribeStatus(aBoolean);
            }
        });
    }

    @Override
    public void changeSubscribeStatus(boolean checked) {
        tb_shares shares = new tb_shares();
        shares.setUserName(userName);
        shares.setSharesType(stockType);
        shares.setSharesNumber(stockSymbol);
        shares.setSharesName(stockName);

        if (checked) {
            mModel.deleteStock(shares, new IDO<Boolean>() {
                @Override
                public void done(Boolean aBoolean) {
                    if (aBoolean) {
                        mView.DeleteSubscribeSuccess();
                        rxManager.post("subscribeUpdate", null);
                        ToastUtils.showShortToast("删除成功");
                    } else {
                        ToastUtils.showShortToast("删除失败");
                    }

                }
            });
        } else {
            mModel.addStock(shares, new IDO<Boolean>() {
                @Override
                public void done(Boolean aBoolean) {

                    if (aBoolean) {
                        mView.addSubscribeSuccess();
                        rxManager.post("subscribeUpdate", null);
                        ToastUtils.showShortToast("添加成功");
                    } else {
                        ToastUtils.showShortToast("添加失败");
                    }

                }
            });
        }
    }

    private void requestData() {
        Observable observable = null;
        switch (stockType) {
            case "sh":
                observable = mModel.getSHData(stockSymbol);
                break;
            case "sz":
                observable = mModel.getSZData(stockSymbol);
                break;
            case "hk":
                observable = mModel.getHKData(stockSymbol)
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
                observable = mModel.getUSData(stockSymbol)
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
                            mView.setStockData(standStockBeanStockResponse.getResult().get(0).getData());
                        }
                    }
                }));

    }

}
