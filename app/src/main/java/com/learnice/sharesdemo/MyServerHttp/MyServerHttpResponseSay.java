package com.learnice.sharesdemo.MyServerHttp;

import org.xutils.common.Callback;

/**
 * Created by Xuebin He on 2016/6/24.
 */
public class MyServerHttpResponseSay implements Callback.CommonCallback<String> {
    IMyServerDataResult iMyServerDataResult;

    public MyServerHttpResponseSay(IMyServerDataResult iMyServerDataResult) {
        this.iMyServerDataResult = iMyServerDataResult;
    }

    @Override
    public void onSuccess(String result) {
            iMyServerDataResult.resultSayList(result);
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
