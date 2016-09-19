package com.learnice.sharesdemo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
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
 * Created by Xuebin He on 2016/6/21.
 */
public class SubscribeRecyclerViewAdapter extends RecyclerView.Adapter<SubscribeRecyclerViewAdapter.MyHolder> {
    LayoutInflater layoutInflater;
    List<Stock> mdata;
    Context context;

    public SubscribeRecyclerViewAdapter(Context context, List<?> mdata) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.mdata = (List<Stock>) mdata;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.trend_recycler_item, null);
        MyHolder holder = new MyHolder(view,recyclerViewListener,recyclerViewOnLongTimeListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        double value=Double.valueOf(mdata.get(position).getLimit());
        BigDecimal bigDecimal=new BigDecimal(value);
        double valueNew=bigDecimal.setScale(2,BigDecimal.ROUND_CEILING).doubleValue();
        String valueLimit=String.valueOf(valueNew);
        if (value<0){
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

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView stockName, stockNum, nowPri, uppic, limit;
        public MyHolder(View itemView, final RecyclerViewListener recyclerListener, final RecyclerViewOnLongTimeListener recyclerViewOnLongTimeListener) {
            super(itemView);
            stockName = (TextView) itemView.findViewById(R.id.trend_recycler_item_stockName);
            stockNum = (TextView) itemView.findViewById(R.id.trend_recycler_item_stockNum);
            nowPri = (TextView) itemView.findViewById(R.id.trend_recycler_item_nowPri);
            uppic = (TextView) itemView.findViewById(R.id.trend_recycler_item_uppic);
            limit = (TextView) itemView.findViewById(R.id.trend_recycler_item_limit);
            if (recyclerListener!=null&&recyclerViewOnLongTimeListener!=null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recyclerListener.onClick(getPosition());
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

    }
    RecyclerViewOnLongTimeListener recyclerViewOnLongTimeListener;
    RecyclerViewListener recyclerViewListener;
    public void setOnItemListener(RecyclerViewListener recyclerListener){
        this.recyclerViewListener=recyclerListener;
    }
    public void  setOnLongTimeListener(RecyclerViewOnLongTimeListener recyclerViewOnLongTimeListener){
        this.recyclerViewOnLongTimeListener=recyclerViewOnLongTimeListener;
    }
}

