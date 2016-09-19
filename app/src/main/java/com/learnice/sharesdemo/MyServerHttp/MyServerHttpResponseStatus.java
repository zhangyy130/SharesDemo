package com.learnice.sharesdemo.MyServerHttp;

import org.xutils.common.Callback;

/**
 * Created by Xuebin He on 2016/6/20.
 */
public class MyServerHttpResponseStatus implements Callback.CommonCallback<String> {
    IMyServerDataResult iMyServerDataResult;

    public MyServerHttpResponseStatus(IMyServerDataResult iMyServerDataResult) {
        this.iMyServerDataResult = iMyServerDataResult;
    }

    @Override
    public void onSuccess(String result) {
        //待解析
        iMyServerDataResult.resultString(result);
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
