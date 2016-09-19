package com.learnice.sharesdemo.MyServerHttp;

import android.util.Log;

import org.xutils.common.Callback;

/**
 * Created by Xuebin He on 2016/6/19.
 */
public class MyServerHttpResponseList implements Callback.CommonCallback<String> {
    IMyServerDataResult iMyServerDataResult;

    public MyServerHttpResponseList(IMyServerDataResult iMyServerDataResult) {
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
