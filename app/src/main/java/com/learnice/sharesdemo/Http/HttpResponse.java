package com.learnice.sharesdemo.Http;

import com.learnice.sharesdemo.Json.SuperJson;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import javax.xml.parsers.FactoryConfigurationError;

/**
 * Created by Xuebin He on 2016/6/17.
 */
public class HttpResponse implements Callback.CommonCallback<String> {
    private IDataResult dataResult;
    private String type;

    public HttpResponse(IDataResult dataResult, String type) {
        this.dataResult = dataResult;
        this.type = type;
    }

    @Override
    public void onSuccess(String result) {
        //等待解析
        if (type.equals(MyURL.SHALL) || type.equals(MyURL.SZALL) ||
                type.equals(MyURL.HKALL) || type.equals(MyURL.USALL)) {
            try {
                JSONObject object = new JSONObject(result);
                if (object.getInt("error_code") == 0) {
                    dataResult.resultList(SuperJson.parseFromJuheRetuenList(type,result));
                } else {

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (type.equals(MyURL.SHONE) || type.equals(MyURL.SZONE) ||
                type.equals(MyURL.HKONE) || type.equals(MyURL.USONE)) {
            try {
                JSONObject object = new JSONObject(result);
                if (object.getString("resultcode").equals("200")) {
                    dataResult.resultObj(SuperJson.parseFromJuheRetuenObj(type,result));
                } else {
                    dataResult.resultObj("股票代号输入有误");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


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
