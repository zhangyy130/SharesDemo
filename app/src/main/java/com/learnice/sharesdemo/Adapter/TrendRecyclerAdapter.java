package com.learnice.sharesdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnice.sharesdemo.DetailActivity;
import com.learnice.sharesdemo.Other.RecyclerViewListener;
import com.learnice.sharesdemo.Other.RecyclerViewOnLongTimeListener;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.Stock.Stock;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Xuebin He on 2016/6/16.
 */
public class TrendRecyclerAdapter extends RecyclerView.Adapter<TrendRecyclerAdapter.Holder> {
    LayoutInflater layoutInflater;
    List<Stock> mdata;
    Context context;

    public TrendRecyclerAdapter(Context context, List<?> mdata) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.mdata = (List<Stock>) mdata;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.trend_recycler_item, null);
        Holder holder = new Holder(view, recyclerViewListener,recyclerViewOnLongTimeListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        double value=Double.valueOf(mdata.get(position).getLimit());
        BigDecimal bigDecimal=new BigDecimal(value);
        double valueNew=bigDecimal.setScale(2,BigDecimal.ROUND_CEILING).doubleValue();
        String valueLimit=String.valueOf(valueNew);
        if (valueNew<0){
            holder.nowPri.setTextColor(Color.rgb(46,125,50));
            holder.uppic.setTextColor(Color.rgb(46,125,50));
            holder.limit.setBackgroundColor(Color.rgb(46,125,50));
        }
        else {

            holder.nowPri.setTextColor(Color.rgb(213,0,0));
            holder.uppic.setTextColor(Color.rgb(213,0,0));
            holder.limit.setBackgroundColor(Color.rgb(213,0,0));
            valueLimit="+"+valueLimit;
        }
        holder.stockName.setText(mdata.get(position).getStockName());
        holder.stockNum.setText(mdata.get(position).getStockNum());
        holder.nowPri.setText(mdata.get(position).getNowPri());
        holder.uppic.setText(mdata.get(position).getUppic());
        holder.limit.setText(valueLimit+"%");
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        TextView stockName, stockNum, nowPri, uppic, limit;
        public Holder(View itemView, final RecyclerViewListener recyclerViewListener,
                      final RecyclerViewOnLongTimeListener recyclerViewOnLongTimeListener) {
            super(itemView);
            stockName = (TextView) itemView.findViewById(R.id.trend_recycler_item_stockName);
            stockNum = (TextView) itemView.findViewById(R.id.trend_recycler_item_stockNum);
            nowPri = (TextView) itemView.findViewById(R.id.trend_recycler_item_nowPri);
            uppic = (TextView) itemView.findViewById(R.id.trend_recycler_item_uppic);
            limit = (TextView) itemView.findViewById(R.id.trend_recycler_item_limit);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewListener.onClick(getPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    recyclerViewOnLongTimeListener.onClick(getPosition());
                    return false;
                }
            });
        }

    }

    RecyclerViewListener recyclerViewListener;
    RecyclerViewOnLongTimeListener recyclerViewOnLongTimeListener;
    public void setOnLongTimeListener(RecyclerViewOnLongTimeListener recyclerViewOnLongTimeListener){
        this.recyclerViewOnLongTimeListener=recyclerViewOnLongTimeListener;
    }
    public void setOnItemListener(RecyclerViewListener recyclerListener) {
        this.recyclerViewListener = recyclerListener;
    }
}
