package com.learnice.sharesdemo.ui.main.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.learnice.base_library.base.BaseFragment;
import com.learnice.sharesdemo.Adapter.TrendRecyclerAdapter;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.ui.main.contract.TrendContract;
import com.learnice.sharesdemo.ui.main.presenter.TrendPresenter;

import butterknife.BindView;


public class Trend extends BaseFragment<TrendPresenter> implements TrendContract.View {

    @BindView(R.id.home_recyclerview)
    RecyclerView homeRecyclerview;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    Context mContext;
    TrendRecyclerAdapter adapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_trend;
    }

    @Override
    public void initView() {
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(mContext, R.color.colorPrimary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
        adapter = new TrendRecyclerAdapter();
        homeRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        homeRecyclerview.setAdapter(adapter);

    }

    @Override
    public void initPresenter() {
        mPresenter = new TrendPresenter(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
}
