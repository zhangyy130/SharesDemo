package com.learnice.sharesdemo.Adapter;

import com.learnice.base_library.base_adapter.BaseAdapter;
import com.learnice.base_library.base_adapter.BaseViewHolder;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.StandStockBean;

/**
 * Created by Xuebin He on 2016/6/21.
 */
public class SubscribeAdapter extends BaseAdapter<StandStockBean> {

    @Override
    protected void bind(BaseViewHolder viewHolder, int layoutRes) {

    }

    @Override
    public int getLayoutRes(int index) {
        return R.layout.trend_recycler_item;
    }

    @Override
    public void convert(BaseViewHolder holder, StandStockBean data, int index) {
        holder.setText(R.id.trend_recycler_item_stockName, data.getName());
        holder.setText(R.id.trend_recycler_item_stockNum, data.getGid());
        holder.setText(R.id.trend_recycler_item_nowPri, data.getNowPri());
        holder.setText(R.id.trend_recycler_item_uppic, data.getIncrease());
        holder.setText(R.id.trend_recycler_item_limit, data.getIncrePer());
    }
}

