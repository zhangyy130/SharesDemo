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

import com.learnice.sharesdemo.Other.RecyclerViewListener;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.Stock.News;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Xuebin He on 2016/6/25.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.NewsHolder>{
    LayoutInflater layoutInflater;
    List<News> list;
    View mHeaderView;
    Context context;
    int[] ints=new int[]{R.drawable.abcd,
            R.drawable.imageimage,
            R.drawable.imageimage,
            R.drawable.imageimage,
            R.drawable.imageimage};
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    public HomeRecyclerViewAdapter(Context context,List<News> list) {
        this.context=context;
        this.layoutInflater=LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderView == null) return TYPE_NORMAL;
        if(position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }
    public void setHeaderView(View mHeaderView){
        this.mHeaderView=mHeaderView;
        notifyItemInserted(0);
    }
    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(mHeaderView != null && viewType == TYPE_HEADER){
            ViewPager viewPager1= (ViewPager) mHeaderView.findViewById(R.id.header_home_view_pager);
            viewPager1.setAdapter(new HomeViewPagerAdapter(context,ints));
            CircleIndicator circleIndicator= (CircleIndicator) mHeaderView.findViewById(R.id.header_home_indictor);
            circleIndicator.setViewPager(viewPager1);
            return new NewsHolder(mHeaderView,recyclerViewListener);
        }
        View view=layoutInflater.inflate(R.layout.home_news_item,parent,false);
        return new NewsHolder(view,recyclerViewListener);
    }

    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        if(getItemViewType(position) == TYPE_HEADER) return;
        holder.title.setText(list.get(position).getTitle());
        holder.source.setText(list.get(position).getSource());
    }

    @Override
    public int getItemCount() {
        return mHeaderView==null?list.size():list.size();
    }

    public  class NewsHolder extends RecyclerView.ViewHolder{
    TextView title,source;
        public NewsHolder(View itemView, final RecyclerViewListener recyclerViewListener) {
            super(itemView);
            if (itemView==mHeaderView) return;
            title= (TextView) itemView.findViewById(R.id.home_news_title);
            source= (TextView) itemView.findViewById(R.id.home_news_source);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewListener.onClick(getPosition());
                }
            });
        }
    }
    RecyclerViewListener recyclerViewListener;
    public void setRecyclerViewListener( RecyclerViewListener recyclerViewListener){
        this.recyclerViewListener=recyclerViewListener;
    }
}
