package com.learnice.sharesdemo.ui.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.learnice.base_library.base.BaseFragment;
import com.learnice.base_library.base_adapter.OnItemClickListener;
import com.learnice.base_library.base_adapter.OnLoadMoreListener;
import com.learnice.sharesdemo.Adapter.TrendAdapter;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.SHStockListBean;
import com.learnice.sharesdemo.ui.details.Activity.DetailsActivity;
import com.learnice.sharesdemo.ui.main.contract.TrendContract;
import com.learnice.sharesdemo.ui.main.presenter.TrendPresenter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public class Trend extends BaseFragment<TrendPresenter> implements TrendContract.View {

    @BindView(R.id.home_recyclerview)
    RecyclerView homeRecyclerview;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    Context mContext;
    TrendAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

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
                adapter.clearData();
                mPresenter.initPage();
                mPresenter.getStockList();
            }
        });
        adapter = new TrendAdapter();
        homeRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
        adapter.openAutoLoadMore(true);
        adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getStockList();
            }
        });
        adapter.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SHStockListBean bean = adapter.getOneData(position);
                Bundle b = new Bundle();
                b.putString("type", bean.getType());
                b.putString("stock", bean.getSymbol());
                b.putString("name",bean.getName());
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("bundle", b);
                mContext.startActivity(intent);
            }
        });
        homeRecyclerview.setAdapter(adapter);

        mPresenter.getStockList();
    }

    @Override
    public void initPresenter() {
        mPresenter = new TrendPresenter(this);
        mPresenter.subscribe();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.unsubscribe();
    }

    @Override
    public void getStockList(List<SHStockListBean> data) {
        if (swipeRefreshLayout.isRefreshing()) swipeRefreshLayout.setRefreshing(false);
        adapter.addData(data);
    }

    @Override
    public void flushStockList() {
        adapter.clearData();
        mPresenter.getStockList();
    }
}
