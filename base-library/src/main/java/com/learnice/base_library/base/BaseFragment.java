package com.learnice.base_library.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Xuebin He on 2017/5/16.
 * e-mail:learnice.he@gmail.com.
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    private Unbinder unbinder;
    protected View rootView;
    protected Context mContext;
    public T mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext =context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
            unbinder= ButterKnife.bind(this, rootView);
        }

        initPresenter();
        initView();
        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.unsubscribe();
    }

    @LayoutRes
    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initPresenter();
}
