package com.learnice.sharesdemo.widget.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.learnice.base_library.base_adapter.BaseAdapter;
import com.learnice.base_library.base_adapter.BaseViewHolder;
import com.learnice.base_library.utils.HandleNumber;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.SHStockListBean;

import java.math.BigDecimal;

/**
 * Created by Xuebin He on 2016/6/16.
 */
public class TrendAdapter extends BaseAdapter<SHStockListBean> {

    Context mContext;

    public TrendAdapter(Context context) {
        this.mContext = context;
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
        holder.setText(R.id.trend_recycler_item_stockName, data.getName());
        holder.setText(R.id.trend_recycler_item_stockNum, data.getSymbol());

        double changePercent = Double.valueOf(data.getChangepercent());
        BigDecimal bigDecimal = new BigDecimal(changePercent);
        double valueNew = bigDecimal.setScale(2, BigDecimal.ROUND_CEILING).doubleValue();

        if (changePercent >= 0) {
            String changePercentReal = "+" + String.valueOf(valueNew) + "%";
            holder.setText(R.id.trend_recycler_item_nowPri, String.valueOf(HandleNumber.handleDoubleTwo(Double.valueOf(data.getTrade()), 2)))
                    .setTextColor(R.id.trend_recycler_item_nowPri, ContextCompat.getColor(mContext, R.color.up))
                    .setText(R.id.trend_recycler_item_uppic, String.valueOf(HandleNumber.handleDoubleTwo(Double.valueOf(data.getPricechange()), 2)))
                    .setTextColor(R.id.trend_recycler_item_uppic, ContextCompat.getColor(mContext, R.color.up))
                    .setText(R.id.trend_recycler_item_limit, changePercentReal)
                    .setBackgroundColor(R.id.trend_recycler_item_limit, ContextCompat.getColor(mContext, R.color.up));
        } else {
            String changePercentReal = String.valueOf(valueNew) + "%";
            holder.setText(R.id.trend_recycler_item_nowPri, String.valueOf(HandleNumber.handleDoubleTwo(Double.valueOf(data.getTrade()), 2)))
                    .setTextColor(R.id.trend_recycler_item_nowPri, ContextCompat.getColor(mContext, R.color.down))
                    .setText(R.id.trend_recycler_item_uppic, String.valueOf(HandleNumber.handleDoubleTwo(Double.valueOf(data.getPricechange()), 2)))
                    .setTextColor(R.id.trend_recycler_item_uppic, ContextCompat.getColor(mContext, R.color.down))
                    .setText(R.id.trend_recycler_item_limit, changePercentReal)
                    .setBackgroundColor(R.id.trend_recycler_item_limit, ContextCompat.getColor(mContext, R.color.down));
        }
    }

}
