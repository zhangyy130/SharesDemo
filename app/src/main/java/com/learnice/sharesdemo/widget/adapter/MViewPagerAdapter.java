package com.learnice.sharesdemo.widget.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learnice.sharesdemo.ui.main.fragment.Home;
import com.learnice.sharesdemo.ui.main.fragment.Subscribe;
import com.learnice.sharesdemo.ui.main.fragment.Trend;

/**
 * Created by Xuebin He on 2016/6/14.
 */
public class MViewPagerAdapter extends FragmentPagerAdapter {
    String[] strings=new String[]{"首页","自选股","行情"};
    Fragment[] fragments=new Fragment[]{new Home(),new Subscribe(),new Trend()};
    Context context;
    public MViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return 3;
    }
}
