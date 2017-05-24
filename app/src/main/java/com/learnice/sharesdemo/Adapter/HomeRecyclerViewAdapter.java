package com.learnice.sharesdemo.Adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnice.base_library.base_adapter.BaseAdapter;
import com.learnice.base_library.base_adapter.BaseViewHolder;
import com.learnice.sharesdemo.Other.RecyclerViewListener;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.Stock.News;
import com.learnice.sharesdemo.bean.NewsBean;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static com.learnice.sharesdemo.R.id.home_news_title;

/**
 * Created by Xuebin He on 2016/6/25.
 */
public class HomeRecyclerViewAdapter extends BaseAdapter<NewsBean> {

    @Override
    protected void bind(BaseViewHolder viewHolder, int layoutRes) {

    }

    @Override
    public int getLayoutRes(int index) {
        return R.layout.home_news_item;
    }

    @Override
    public void convert(BaseViewHolder holder, NewsBean data, int index) {
        holder.setText(R.id.home_news_title,data.getTitle());
        holder.setText(R.id.home_news_source,data.getAuthor_name());
    }
}
