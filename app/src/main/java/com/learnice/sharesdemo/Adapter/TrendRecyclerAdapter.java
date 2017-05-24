package com.learnice.sharesdemo.Adapter;

import com.learnice.base_library.base_adapter.BaseAdapter;
import com.learnice.base_library.base_adapter.BaseViewHolder;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.StockListBean;

/**
 * Created by Xuebin He on 2016/6/16.
 */
public class TrendRecyclerAdapter extends BaseAdapter<StockListBean> {

    public TrendRecyclerAdapter() {
    }

    @Override
    protected void bind(BaseViewHolder viewHolder, int layoutRes) {

    }

    @Override
    public int getLayoutRes(int index) {
        return R.layout.trend_recycler_item;
    }

    @Override
    public void convert(BaseViewHolder holder, StockListBean data, int index) {

    }

}
