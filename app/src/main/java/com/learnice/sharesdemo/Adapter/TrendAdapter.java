package com.learnice.sharesdemo.Adapter;

import com.learnice.base_library.base_adapter.BaseAdapter;
import com.learnice.base_library.base_adapter.BaseViewHolder;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.SHStockListBean;

/**
 * Created by Xuebin He on 2016/6/16.
 */
public class TrendAdapter extends BaseAdapter<SHStockListBean> {

    public TrendAdapter() {
    }

    @Override
    protected void bind(BaseViewHolder viewHolder, int layoutRes) {

    }

    @Override
    public int getLayoutRes(int index) {
        return R.layout.trend_recycler_item;
    }

    @Override
    public void convert(BaseViewHolder holder, SHStockListBean data, int index) {
        holder.setText(R.id.trend_recycler_item_stockName,data.getName());
        holder.setText(R.id.trend_recycler_item_stockNum,data.getSymbol());
        holder.setText(R.id.trend_recycler_item_nowPri,data.getTrade());
        holder.setText(R.id.trend_recycler_item_uppic,data.getPricechange());
        holder.setText(R.id.trend_recycler_item_limit,data.getChangepercent());
    }

}
