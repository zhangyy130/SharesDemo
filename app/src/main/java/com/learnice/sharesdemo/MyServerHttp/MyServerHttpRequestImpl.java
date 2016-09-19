package com.learnice.sharesdemo.MyServerHttp;

import com.learnice.sharesdemo.Http.MyURL;
import com.learnice.sharesdemo.Stock.Say;
import com.learnice.sharesdemo.Stock.Shares;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Xuebin He on 2016/6/19.
 */
public class MyServerHttpRequestImpl implements IMyServerHttpRequest {



    @Override
    public void getServerData(String actionCode, String name, Shares shares, Callback.CommonCallback<String> commonCallback) {
        RequestParams params=new RequestParams(MyURL.MY_SERVWE);
        params.addBodyParameter(MyParams.ACTION_CODE,actionCode);
        params.addBodyParameter(MyParams.NAME,name);
        params.addBodyParameter(MyParams.SHARES_TYPE,shares.getSharesType());
        params.addBodyParameter(MyParams.SHARES_NAME,shares.getSharesName());
        x.http().get(params,commonCallback);
    }

    @Override
    public void checkUser(String actionCode, String name, String pass, Callback.CommonCallback<String> commonCallback) {
        RequestParams params=new RequestParams(MyURL.MY_SERVWE);
        params.addBodyParameter(MyParams.ACTION_CODE,actionCode);
        params.addBodyParameter(MyParams.NAME,name);
        params.addBodyParameter(MyParams.PASS,pass);
        x.http().get(params,commonCallback);
    }

    @Override
    public void uploadSay(String actionCode, Say say, Callback.CommonCallback<String> commonCallback) {
        RequestParams params=new RequestParams(MyURL.MY_SERVWE);
        params.addBodyParameter(MyParams.ACTION_CODE,actionCode);
        params.addBodyParameter(MyParams.NAME,say.getUserName());
        params.addBodyParameter(MyParams.SHARES_TYPE,say.getSharesType());
        params.addBodyParameter(MyParams.SHARES_NAME,say.getSharesName());
        params.addBodyParameter(MyParams.CONTENT,say.getContent());
        params.addBodyParameter(MyParams.CONTENT_TIME,say.getContentTime());
        x.http().get(params,commonCallback);
    }

}
