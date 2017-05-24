package com.learnice.sharesdemo.ui.main.model;

import com.learnice.sharesdemo.Http.MyURL;
import com.learnice.sharesdemo.api.Api;
import com.learnice.sharesdemo.api.HostType;
import com.learnice.sharesdemo.bean.NewsListResponse;
import com.learnice.sharesdemo.bean.NewsBean;
import com.learnice.sharesdemo.ui.main.contract.HomeContract;

import rx.Observable;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class HomeModel implements HomeContract.Model {
    public final String NEWS_TYPE = "caijing";

    @Override
    public Observable<NewsListResponse<NewsBean>> loadNews() {

        return Api.getDefault(HostType.JU_HE_NEWS)
                .getNewsData(MyURL.NEWS_KEY, NEWS_TYPE);
    }
}
