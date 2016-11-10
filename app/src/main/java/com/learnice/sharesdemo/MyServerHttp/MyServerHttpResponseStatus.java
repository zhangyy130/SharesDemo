package com.learnice.sharesdemo.MyServerHttp;

import org.xutils.common.Callback;

/**
 * Created by Xuebin He on 2016/6/20.
 */
public class MyServerHttpResponseStatus implements Callback.CommonCallback<String> {
    IMyServerDataResult iMyServerDataResult;
    int operateCode = -1;

    public MyServerHttpResponseStatus(IMyServerDataResult iMyServerDataResult) {
        this.iMyServerDataResult = iMyServerDataResult;
    }

    public MyServerHttpResponseStatus(IMyServerDataResult iMyServerDataResult, int operateCode) {
        this.iMyServerDataResult = iMyServerDataResult;
        this.operateCode = operateCode;
    }

    @Override
    public void onSuccess(String result) {
        //待解析
        if (operateCode == -1) {
            iMyServerDataResult.resultString(result);
        } else {
            iMyServerDataResult.resultSayList(result, operateCode);
        }
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
