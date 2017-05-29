package com.learnice.sharesdemo.widget.adapter;

import com.learnice.base_library.base_adapter.BaseAdapter;
import com.learnice.base_library.base_adapter.BaseViewHolder;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.NewsBean;

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
