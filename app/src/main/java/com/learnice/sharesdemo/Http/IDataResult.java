package com.learnice.sharesdemo.Http;

import java.util.List;

/**
 * Created by Xuebin He on 2016/6/17.
 */
public interface IDataResult {
    public void resultObj(Object object);
    public void resultList(List<?> list);
}
