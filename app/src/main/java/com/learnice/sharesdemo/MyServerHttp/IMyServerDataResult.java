package com.learnice.sharesdemo.MyServerHttp;

import java.util.List;

/**
 * Created by Xuebin He on 2016/6/19.
 */
public interface IMyServerDataResult {
    /**
     *
     * @param data
     */
    public void resultString(Object data);

    public void resultSayList(String list);
}
