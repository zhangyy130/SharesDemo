package com.learnice.sharesdemo.MyServerHttp;

import com.learnice.sharesdemo.Stock.Say;
import com.learnice.sharesdemo.Stock.Shares;

import org.xutils.common.Callback;

/**
 * Created by Xuebin He on 2016/6/19.
 */
public interface IMyServerHttpRequest {
    /**
     *
     * @param actionCode
     * @param name
     * @param shares
     * @param commonCallback
     */
    public void getServerData(String actionCode,String name, Shares shares,Callback.CommonCallback<String> commonCallback);

    /**
     *
     * @param actionCode
     * @param name
     * @param pass
     * @param commonCallback
     */

    public void checkUser(String actionCode, String name, String pass, Callback.CommonCallback<String> commonCallback);

    /**
     *
     * @param actionCode
     * @param say
     * @param commonCallback
     */
    public void uploadSay(String actionCode, Say say, Callback.CommonCallback<String> commonCallback);

}
