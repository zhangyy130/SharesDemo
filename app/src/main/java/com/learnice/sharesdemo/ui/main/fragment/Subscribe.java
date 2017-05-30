package com.learnice.sharesdemo.ui.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.learnice.base_library.base.BaseFragment;
import com.learnice.base_library.base_adapter.OnItemClickListener;
import com.learnice.base_library.base_adapter.OnLoadMoreListener;
import com.learnice.sharesdemo.widget.adapter.SubscribeAdapter;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.StandStockBean;
import com.learnice.sharesdemo.ui.details.Activity.DetailsActivity;
import com.learnice.sharesdemo.ui.main.contract.SubscribeContract;
import com.learnice.sharesdemo.ui.main.presenter.SubscribePresenter;

import butterknife.BindView;

public class Subscribe extends BaseFragment<SubscribePresenter> implements SubscribeContract.View {

    @BindView(R.id.no_subscribe)
    TextView noSubscribe;
    @BindView(R.id.subscribe_recyclerview)
    RecyclerView subscribeRecyclerview;
    @BindView(R.id.subscribe_swipeRefreshLayuot)
    SwipeRefreshLayout subscribeSwipeRefreshLayuot;

    SubscribeAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_subscribe;
    }

    @Override
    public void initView() {
        adapter = new SubscribeAdapter(mContext);
        adapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                StandStockBean bean = adapter.getOneData(position);
                Bundle b = new Bundle();
                b.putString("type", bean.getType());
                b.putString("stock", bean.getGid());
                b.putString("name", bean.getName());
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("bundle", b);
                mContext.startActivity(intent);
            }
        });
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
        subscribeRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        subscribeRecyclerview.setAdapter(adapter);
        mPresenter.getSubscribeData();
        subscribeSwipeRefreshLayuot.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.colorPrimary));
        subscribeSwipeRefreshLayuot.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.clearData();
                mPresenter.getSubscribeData();
            }
        });
    }

    @Override
    public void initPresenter() {
        mPresenter = new SubscribePresenter(this);
        mPresenter.subscribe();
    }

    @Override
    public void AddStockData(StandStockBean data) {
        noSubscribe.setVisibility(View.GONE);
        adapter.addBean(data);
    }

    @Override
    public void LoadComplete() {
        if (subscribeSwipeRefreshLayuot.isRefreshing()) {
            subscribeSwipeRefreshLayuot.setRefreshing(false);
        }

    }

    @Override
    public void clearData() {
        adapter.clearData();
    }

    @Override
    public void subscribeIsNull() {
        noSubscribe.setVisibility(View.VISIBLE);
    }

}
