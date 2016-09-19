package com.learnice.sharesdemo.HttpGetNews;

import org.xutils.common.Callback;

/**
 * Created by Xuebin He on 2016/6/25.
 */
public interface IRequestNews {
    public void getNews(Callback.CommonCallback<String> commonCallback);
}
