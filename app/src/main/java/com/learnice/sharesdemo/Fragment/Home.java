package com.learnice.sharesdemo.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnice.sharesdemo.AboutMeActivity;
import com.learnice.sharesdemo.Adapter.HomeRecyclerViewAdapter;
import com.learnice.sharesdemo.HttpGetNews.INewsData;
import com.learnice.sharesdemo.HttpGetNews.RequestNewsImpl;
import com.learnice.sharesdemo.HttpGetNews.ResponseNews;
import com.learnice.sharesdemo.Json.ParseNews;
import com.learnice.sharesdemo.Other.RecyclerViewListener;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.Stock.News;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class Home extends Fragment implements INewsData{
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    List<News> list;
    RecyclerView recyclerView;
    HomeRecyclerViewAdapter adapter;
    View rootView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null==rootView){
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
            recyclerView= (RecyclerView) rootView.findViewById(R.id.home_news_recycler);

            new RequestNewsImpl().getNews(new ResponseNews(this));
            list=new ArrayList<News>();
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            View headerView=getActivity().getLayoutInflater().inflate(R.layout.viewpager_layout_header,recyclerView,false);
            adapter=new HomeRecyclerViewAdapter(getActivity(),list);
            recyclerView.setAdapter(adapter);
            adapter.setHeaderView(headerView);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView!=null){
            ((ViewGroup)rootView.getParent()).removeView(rootView);
        }
    }

    @Override
    public void NewsString(String result) {
        parseNews(result);
    }
    public void parseNews(String data){
        try {
            JSONObject object=new JSONObject(data);
            if (object.getString("reason").equals("成功的返回")){
                JSONObject result=object.getJSONObject("result");
                list.addAll(ParseNews.pareNews(result));
                adapter.notifyDataSetChanged();
                adapter.setRecyclerViewListener(new RecyclerViewListener() {
                    @Override
                    public void onClick(int positon) {
                        Intent intent=new Intent(getActivity(), AboutMeActivity.class);
                        intent.putExtra("url",list.get(positon).getArticle_url());
                        getActivity().startActivity(intent);
                        //getActivity().overridePendingTransition(R.anim.anim_in_right_left,R.anim.anim_out_right_left);
                    }
                });
            }
            else {
                Snackbar.make(viewPager,"新闻获取失败",Snackbar.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
