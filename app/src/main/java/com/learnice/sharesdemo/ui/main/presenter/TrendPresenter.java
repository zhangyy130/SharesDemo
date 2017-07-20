package com.learnice.sharesdemo.ui.main.presenter;

import android.os.Handler;
import android.util.Log;

import com.learnice.base_library.baserx.RxManager;
import com.learnice.sharesdemo.bean.HKStockListBean;
import com.learnice.sharesdemo.bean.SHStockListBean;
import com.learnice.sharesdemo.bean.SZStockListBean;
import com.learnice.sharesdemo.bean.StockListResponse;
import com.learnice.sharesdemo.bean.StockType;
import com.learnice.sharesdemo.bean.USStockListBean;
import com.learnice.sharesdemo.ui.main.contract.TrendContract;
import com.learnice.sharesdemo.ui.main.model.TrendModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.learnice.sharesdemo.bean.StockType.SH;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class TrendPresenter implements TrendContract.Presenter {
    private TrendContract.View mView;
    private TrendModel mModel;
    private RxManager rxManager;
    private StockType stockType;
    private int page;

    public TrendPresenter(TrendContract.View view) {
        this.mView = view;
        mModel = new TrendModel();
        rxManager = new RxManager();
        page = 0;
        stockType = SH;
    }

    @Override
    public void subscribe() {
        rxManager.on("StockType", new Action1<Object>() {
            @Override
            public void call(Object object) {
                stockType = (StockType) object;
                page = 0;
                mView.flushStockList();
            }
        });
    }

    @Override
    public void unsubscribe() {
        rxManager.clear();
    }

    @Override
    public void getStockList() {
        Observable observable = null;
        ++page;
        switch (stockType) {
            case SH:
                observable = mModel.getSHStockList(page)
                        .map(new Func1<StockListResponse<SHStockListBean>, StockListResponse<SHStockListBean>>() {

                    @Override
                    public StockListResponse<SHStockListBean> call(StockListResponse<SHStockListBean> shStockListBeanStockListResponse) {
                        for (SHStockListBean bean : shStockListBeanStockListResponse.getResult().getData()) {
                            bean.setType("sh");
                        }
                        return shStockListBeanStockListResponse;
                    }
                });
                break;
            case SZ:
                observable = mModel.getSZStockList(page)
                        .map(new Func1<StockListResponse<SZStockListBean>, StockListResponse<SHStockListBean>>() {
                            @Override
                            public StockListResponse<SHStockListBean> call(StockListResponse<SZStockListBean> szStockListBeanStockListResponse) {
                                //初始化返回类
                                StockListResponse<SHStockListBean> stockListBean = new StockListResponse<>();
                                stockListBean.setError_code(szStockListBeanStockListResponse.getError_code());
                                stockListBean.setReason(szStockListBeanStockListResponse.getReason());
                                //初始化返回数据
                                List<SHStockListBean> list = new ArrayList<>();
                                for (SZStockListBean bean : szStockListBeanStockListResponse.getResult().getData()) {
                                    SHStockListBean beanReal = new SHStockListBean();
                                    beanReal.setName(bean.getName());
                                    beanReal.setSymbol(bean.getSymbol());
                                    beanReal.setTrade(bean.getTrade());
                                    beanReal.setChangepercent(bean.getChangepercent());
                                    beanReal.setPricechange(bean.getPricechange());
                                    beanReal.setType("sz");
                                    list.add(beanReal);
                                }
                                //初始化结果类
                                StockListResponse.ResultBean<SHStockListBean> resultBean = new StockListResponse.ResultBean<SHStockListBean>();
                                resultBean.setNum(szStockListBeanStockListResponse.getResult().getNum());
                                resultBean.setPage(szStockListBeanStockListResponse.getResult().getPage());
                                resultBean.setTotalCount(szStockListBeanStockListResponse.getResult().getTotalCount());
                                resultBean.setData(list);
                                //设置结果类
                                stockListBean.setResult(resultBean);
                                return stockListBean;
                            }
                        });
                break;
            case HK:
                observable = mModel.getHKStockList(page)
                        .map(new Func1<StockListResponse<HKStockListBean>, StockListResponse<SHStockListBean>>() {
                            @Override
                            public StockListResponse<SHStockListBean> call(StockListResponse<HKStockListBean> hkStockListBeanStockListResponse) {
                                //初始化返回类
                                StockListResponse<SHStockListBean> stockListBean = new StockListResponse<>();
                                stockListBean.setError_code(hkStockListBeanStockListResponse.getError_code());
                                stockListBean.setReason(hkStockListBeanStockListResponse.getReason());
                                //初始化返回数据
                                List<SHStockListBean> list = new ArrayList<>();
                                for (HKStockListBean bean : hkStockListBeanStockListResponse.getResult().getData()) {
                                    SHStockListBean beanReal = new SHStockListBean();
                                    beanReal.setName(bean.getName());
                                    beanReal.setSymbol(bean.getSymbol());
                                    beanReal.setTrade(bean.getLasttrade());
                                    beanReal.setChangepercent(bean.getChangepercent());
                                    beanReal.setPricechange(bean.getPricechange());
                                    beanReal.setType("hk");
                                    list.add(beanReal);
                                }
                                //初始化结果类
                                StockListResponse.ResultBean<SHStockListBean> resultBean = new StockListResponse.ResultBean<SHStockListBean>();
                                resultBean.setNum(hkStockListBeanStockListResponse.getResult().getNum());
                                resultBean.setPage(hkStockListBeanStockListResponse.getResult().getPage());
                                resultBean.setTotalCount(hkStockListBeanStockListResponse.getResult().getTotalCount());
                                resultBean.setData(list);
                                //设置结果类
                                stockListBean.setResult(resultBean);
                                return stockListBean;
                            }
                        });
                break;
            case US:
                observable = mModel.getUSStockList(page).map(new Func1<StockListResponse<USStockListBean>, StockListResponse<SHStockListBean>>() {

                    @Override
                    public StockListResponse<SHStockListBean> call(StockListResponse<USStockListBean> usStockListBeanStockListResponse) {
                        //初始化返回类
                        StockListResponse<SHStockListBean> stockListBean = new StockListResponse<>();
                        stockListBean.setError_code(usStockListBeanStockListResponse.getError_code());
                        stockListBean.setReason(usStockListBeanStockListResponse.getReason());
                        //初始化返回数据
                        List<SHStockListBean> list = new ArrayList<>();
                        for (USStockListBean bean : usStockListBeanStockListResponse.getResult().getData()) {
                            SHStockListBean beanReal = new SHStockListBean();
                            beanReal.setName(bean.getCname());
                            beanReal.setSymbol(bean.getSymbol());
                            beanReal.setTrade(bean.getPrice());
                            beanReal.setChangepercent(bean.getChg());
                            beanReal.setPricechange(bean.getDiff());
                            beanReal.setType("us");
                            list.add(beanReal);
                        }
                        //初始化结果类
                        StockListResponse.ResultBean<SHStockListBean> resultBean = new StockListResponse.ResultBean<SHStockListBean>();
                        resultBean.setNum(usStockListBeanStockListResponse.getResult().getNum());
                        resultBean.setPage(usStockListBeanStockListResponse.getResult().getPage());
                        resultBean.setTotalCount(usStockListBeanStockListResponse.getResult().getTotalCount());
                        resultBean.setData(list);
                        //设置结果类
                        stockListBean.setResult(resultBean);
                        return stockListBean;
                    }
                });
                break;
        }
        rxManager.add(observable
                .delay(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<StockListResponse<SHStockListBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.d("------------", throwable.getMessage());
                    }

                    @Override
                    public void onNext(StockListResponse<SHStockListBean> stockListBeanStockListResponse) {
                        if (stockListBeanStockListResponse.getError_code() == 0) {
                            mView.getStockList(stockListBeanStockListResponse.getResult().getData());
                        }
                    }
                }));
    }

    @Override
    public void initPage() {
        page = 0;
    }

}
