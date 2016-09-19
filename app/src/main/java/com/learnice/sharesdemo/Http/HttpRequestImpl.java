package com.learnice.sharesdemo.Http;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by Xuebin He on 2016/6/17.
 */
public class HttpRequestImpl implements IHttpRequest {
    @Override
    public void getData(String type, Callback.CommonCallback<String> commonCallback) {
        String url=null;
        if (type.equals(MyURL.SHALL)) {
            url=MyURL.URL_SH_ALL;
        } else if (type.equals(MyURL.SZALL)) {
            url=MyURL.URL_SZ_ALL;
        } else if (type.equals(MyURL.HKALL)) {
            url=MyURL.URL_HK_ALL;
        } else if (type.equals(MyURL.USALL)) {
            url=MyURL.URL_US_ALL;
        }
        RequestParams params=new RequestParams(url);
        x.http().get(params,commonCallback);

    }

    @Override
    public void getData(String type, String num, Callback.CommonCallback<String> commonCallback) {
        String url=null;
        if (type.equals(MyURL.SHONE)){
            url=MyURL.URL_SH_SINGLE+num;
        }
        else if (type.equals(MyURL.SZONE)){
            url=MyURL.URL_SZ_SINGLE+num;
        }
        else if (type.equals(MyURL.HKONE)){
            num=num.substring(2,num.length());
            url=MyURL.URL_HK_SINGLE+num;
        }
        else if (type.equals(MyURL.USONE)){
            url=MyURL.URL_US_SINGLE+num;
        }
        else if (type.equals(MyURL.SHALL)){
            url=MyURL.URL_SH_ALL+num;
        }
        else if (type.equals(MyURL.SZALL)){
            url=MyURL.URL_SZ_ALL+num;
        }
        else if (type.equals(MyURL.HKALL)){
            url=MyURL.URL_HK_ALL+num;
        }
        else if (type.equals(MyURL.USALL)){
            url=MyURL.URL_US_ALL+num;
        }
        RequestParams params=new RequestParams(url);
        x.http().get(params,commonCallback);
    }

    @Override
    public void getData(String type, List<String> list, Callback.CommonCallback<String> commonCallback) {

    }
}
