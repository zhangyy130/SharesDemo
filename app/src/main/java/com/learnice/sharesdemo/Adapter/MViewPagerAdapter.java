package com.learnice.sharesdemo.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.learnice.sharesdemo.Fragment.Home;
import com.learnice.sharesdemo.Fragment.Subscribe;
import com.learnice.sharesdemo.Fragment.Trend;

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
