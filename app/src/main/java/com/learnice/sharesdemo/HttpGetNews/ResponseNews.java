package com.learnice.sharesdemo.HttpGetNews;

import org.xutils.common.Callback;

/**
 * Created by Xuebin He on 2016/6/25.
 */
public class ResponseNews implements Callback.CommonCallback<String> {
    INewsData iNewsData;

    public ResponseNews(INewsData iNewsData) {
        this.iNewsData = iNewsData;
    }

    @Override
    public void onSuccess(String result) {
        iNewsData.NewsString(result);
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {

    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
