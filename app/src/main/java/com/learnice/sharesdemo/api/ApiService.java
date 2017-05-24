package com.learnice.sharesdemo.api;

import com.learnice.sharesdemo.bean.NewsListResponse;
import com.learnice.sharesdemo.bean.NewsBean;
import com.learnice.sharesdemo.bean.StockListBean;
import com.learnice.sharesdemo.bean.StockListResponse;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public interface ApiService {

    //获取新闻
    @POST("index")
    Observable<NewsListResponse<NewsBean>> getNewsData(
            @Query("key") String key,
            @Query("type") String type);

    //http://web.juhe.cn:8080/finance/stock/shall?key=bcafd8d700363a408b74269134b4e744&page=1 获取上海list
    @POST("shall")
    Observable<StockListResponse<StockListBean>> getSHStockList(
            @Query("key") String key,
            @Query("page") String page
    );

    //http://web.juhe.cn:8080/finance/stock/szall?key=您申请的APPKEY&page=1 获取深圳
    @POST("szall")
    Observable<StockListResponse<StockListBean>> getSZStockList(
            @Query("key") String key,
            @Query("page") String page
    );

    //http://web.juhe.cn:8080/finance/stock/usaall?key=您申请的KEY&page=1 获取美国
    @POST("usaall")
    Observable<StockListResponse<StockListBean>> getUSStockList(
            @Query("key") String key,
            @Query("page") String page
    );

    //http://web.juhe.cn:8080/finance/stock/hkall?key=您申请的KEY&page=1  获取香港
    @POST("hkall")
    Observable<StockListResponse<StockListBean>> getHKStockList(
            @Query("key") String key,
            @Query("page") String page
    );
}
