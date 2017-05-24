package com.learnice.sharesdemo.ui.main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import com.learnice.base_library.base.BaseFragment;
import com.learnice.sharesdemo.Adapter.HomeRecyclerViewAdapter;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.NewsBean;
import com.learnice.sharesdemo.ui.main.contract.HomeContract;
import com.learnice.sharesdemo.ui.main.presenter.HomePresenter;
import java.util.List;
import butterknife.BindView;


public class Home extends BaseFragment<HomePresenter> implements HomeContract.View {

    HomeRecyclerViewAdapter adapter;

    @BindView(R.id.home_frg_progressBar)
    ProgressBar homeFrgProgressBar;
    @BindView(R.id.home_news_recycler)
    RecyclerView homeNewsRecycler;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        homeNewsRecycler.setLayoutManager(linearLayoutManager);
        adapter = new HomeRecyclerViewAdapter();
        homeNewsRecycler.setAdapter(adapter);

        initData();
    }

    private void initData() {
        mPresenter.loadNews();
        homeFrgProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void initPresenter() {
        mPresenter = new HomePresenter(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }


    @Override
    public void setNewsData(List<NewsBean> data) {
        adapter.setData(data);
        homeFrgProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
}
