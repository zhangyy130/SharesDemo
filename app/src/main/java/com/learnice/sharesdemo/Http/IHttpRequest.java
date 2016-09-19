package com.learnice.sharesdemo.Http;

import org.xutils.common.Callback;

import java.util.List;

/**
 * Created by Xuebin He on 2016/6/17.
 */
public interface IHttpRequest {
    /**
     * 查询某行情
     * @param type
     * @param commonCallback
     */
    public void getData(String type,Callback.CommonCallback<String> commonCallback);

    /**
     * 查询一只股票信息
     * @param type
     * @param num
     * @param commonCallback
     */
    public void getData(String type,String num, Callback.CommonCallback<String> commonCallback);

    /**
     * 查询一组股票信息
     * @param type
     * @param list
     * @param commonCallback
     */
    public void getData(String type,List<String> list, Callback.CommonCallback<String> commonCallback);

}
