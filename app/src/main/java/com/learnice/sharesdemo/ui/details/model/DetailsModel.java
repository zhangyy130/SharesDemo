package com.learnice.sharesdemo.ui.details.model;

import com.learnice.sharesdemo.FunInterface.IDO;
import com.learnice.sharesdemo.FunInterface.Idone;
import com.learnice.sharesdemo.bean.Shares;
import com.learnice.sharesdemo.api.Api;
import com.learnice.sharesdemo.api.ApiConstants;
import com.learnice.sharesdemo.api.HostType;
import com.learnice.sharesdemo.app.App;
import com.learnice.sharesdemo.bean.HKStockBean;
import com.learnice.sharesdemo.bean.StandStockBean;
import com.learnice.sharesdemo.bean.StockResponse;
import com.learnice.sharesdemo.bean.USStockBean;
import com.learnice.sharesdemo.bean.tb_say;
import com.learnice.sharesdemo.bean.tb_shares;
import com.learnice.sharesdemo.ui.details.contract.DetailsContract;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import rx.Observable;


/**
 * Created by Xuebin He on 2017/5/25.
 * e-mail:learnice.he@gmail.com.
 */

public class DetailsModel implements DetailsContract.Model {

    public static final String OK = "成功";
    public static final String OCCUR_EXCEPTION = "发生异常";

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

    @Override
    public void senSay(tb_say say, final Idone idone) {
        say.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    idone.done(OK);
                } else {
                    idone.done(OCCUR_EXCEPTION);
                }
            }
        });
    }

    @Override
    public void getSayData(String stockType, String stockSymbol, final IDO<List<tb_say>> ido, final IDO<String> idos) {
        BmobQuery<tb_say> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("sharesType", stockType);
        bmobQuery.addWhereEqualTo("sharesNumber", stockSymbol);
        bmobQuery.findObjects(new FindListener<tb_say>() {
            @Override
            public void done(List<tb_say> list, BmobException e) {
                if (e == null) {
                    ido.done(list);
                } else {
                    idos.done(e.getMessage());
                }
            }
        });
    }

    @Override
    public void getSubscribeStatus(String userName, String stockType, String stockSymbol, final IDO<Boolean> ido) {
        BmobQuery<tb_shares> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userName", userName);
        bmobQuery.addWhereEqualTo("sharesType", stockType);
        bmobQuery.addWhereEqualTo("sharesNumber", stockSymbol);
        bmobQuery.findObjects(new FindListener<tb_shares>() {
            @Override
            public void done(List<tb_shares> list, BmobException e) {
                if (e == null) {

                    if (list.size() > 0) {
                        ido.done(true);
                    } else {
                        ido.done(false);
                    }

                } else {

                }
            }
        });
    }

    @Override
    public void deleteStock(final tb_shares shares, final IDO<Boolean> ido) {

        BmobQuery<tb_shares> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("userName", shares.getUserName());
        bmobQuery.addWhereEqualTo("sharesType", shares.getSharesType());
        bmobQuery.addWhereEqualTo("sharesNumber", shares.getSharesNumber());
        bmobQuery.findObjects(new FindListener<tb_shares>() {
            @Override
            public void done(List<tb_shares> list, BmobException e) {
                if (e == null) {
                    String objId = list.get(0).getObjectId();
                    tb_shares tb_shares = new tb_shares();
                    tb_shares.setObjectId(objId);
                    tb_shares.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                            if (e == null) {
                                App.getInstance().dbServices.delete(new Shares(shares.getSharesType(), shares.getSharesNumber()));
                                ido.done(true);
                            } else {
                                ido.done(false);
                            }

                        }
                    });

                } else {
                    ido.done(false);
                }
            }
        });

    }

    @Override
    public void addStock(final tb_shares shares, final IDO<Boolean> ido) {
        shares.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    App.getInstance().dbServices.add(new Shares(shares.getSharesType(), shares.getSharesNumber()));
                    ido.done(true);
                } else {
                    ido.done(false);
                }
            }
        });
    }

}
