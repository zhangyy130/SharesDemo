package com.learnice.sharesdemo.HttpGetNews;

import com.learnice.sharesdemo.Http.MyURL;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Xuebin He on 2016/6/25.
 */
public class RequestNewsImpl implements IRequestNews {
    @Override
    public void getNews(Callback.CommonCallback<String> commonCallback) {
        RequestParams params=new RequestParams(MyURL.URL_NEWS);
        x.http().get(params,commonCallback);
    }
}
