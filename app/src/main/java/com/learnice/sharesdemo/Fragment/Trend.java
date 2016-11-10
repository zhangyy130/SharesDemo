package com.learnice.sharesdemo.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.learnice.sharesdemo.Adapter.TrendRecyclerAdapter;
import com.learnice.sharesdemo.Database.DbServices;
import com.learnice.sharesdemo.DetailActivity;
import com.learnice.sharesdemo.Http.HttpRequestImpl;
import com.learnice.sharesdemo.Http.HttpResponse;
import com.learnice.sharesdemo.Http.IDataResult;
import com.learnice.sharesdemo.Http.MyURL;
import com.learnice.sharesdemo.MyServerHttp.IMyServerDataResult;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpRequestImpl;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpResponseStatus;
import com.learnice.sharesdemo.Other.MyScrollListener;
import com.learnice.sharesdemo.Other.RecyclerViewListener;
import com.learnice.sharesdemo.Other.RecyclerViewOnLongTimeListener;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.SharedData.AboutUser;
import com.learnice.sharesdemo.Stock.Shares;
import com.learnice.sharesdemo.Stock.Stock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Trend extends Fragment implements SwipeRefreshLayout.OnRefreshListener, IDataResult,IMyServerDataResult{

    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;
    List<Stock> list;
    String type;
    LinearLayoutManager linearLayoutManager;
    TrendRecyclerAdapter trendRecyclerAdapter;
    int currentCount;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentCount=0;
        if (rootView==null){
            rootView = inflater.inflate(R.layout.fragment_trend, container, false);
            refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayuot);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.home_recyclerview);
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.setColorSchemeColors(R.color.colorPrimaryDark,
                    R.color.colorAccent,
                    R.color.colorPrimary);
            list=new ArrayList<Stock>();
            setRecyclerView(rootView);
            //------------------------
            type = MyURL.SHALL;
            new HttpRequestImpl().getData(type, "1", new HttpResponse(this, type));
            trendRecyclerAdapter=new TrendRecyclerAdapter(getActivity(),list);
            recyclerView.setAdapter(trendRecyclerAdapter);
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onRefresh() {
        //refreshLayout.setRefreshing(true);
        new HttpRequestImpl().getData(type,"1",new HttpResponse(this,type));

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(false);
//            }
//        }, 1000);
    }

    @Override
    public void resultObj(Object object) {

    }

    @Override
    public void resultList(List<?> list) {
        refreshLayout.setRefreshing(false);
        currentCount=this.list.size();
        this.list.addAll((Collection<? extends Stock>) list);
        trendRecyclerAdapter.notifyItemRangeInserted(currentCount,this.list.size());
        if (list.size()<30) {
            //recyclerView.setAdapter(trendRecyclerAdapter);
            trendRecyclerAdapter.setOnItemListener(new RecyclerViewListener() {
                @Override
                public void onClick(int positon) {
                    Intent intent=new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("type",2);
                    intent.putExtra("stock",Trend.this.list.get(positon));
                    getActivity().startActivity(intent);
                }
            });
            trendRecyclerAdapter.setOnLongTimeListener(new RecyclerViewOnLongTimeListener() {
                @Override
                public void onClick(final int position) {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setMessage("添加到自选股?")
                            .setNegativeButton("不用了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setPositiveButton("好", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Stock stock=Trend.this.list.get(position);
                                    Cursor cursor=new DbServices(getActivity()).selectOne(new Shares(stock.getStockType(),stock.getStockNum()));
                                    if (cursor.getCount()==0) {
                                        new DbServices(getActivity()).add(new Shares(stock.getStockType(), stock.getStockNum()));
                                       getActivity().sendBroadcast(new Intent("insert"));
                                        new MyServerHttpRequestImpl().getServerData("201",
                                                new AboutUser(getActivity()).readName(),
                                                new Shares(stock.getStockType(), stock.getStockNum()),
                                                new MyServerHttpResponseStatus(Trend.this));
                                    }
                                    else {
                                        Snackbar.make(refreshLayout,"已存在",Snackbar.LENGTH_SHORT).show();
                                    }
                                }
                            }).show();
                }
            });
        }
        else {
            trendRecyclerAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void resultString(Object data) {

        Snackbar.make(refreshLayout,"添加成功",Snackbar.LENGTH_SHORT).show();
        refresh.refresh();

    }

    @Override
    public void resultSayList(String list) {

    }

    @Override
    public void resultSayList(String list, int position) {

    }


    public void tabSh() {
        list.clear();
        trendRecyclerAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(true);
        type = MyURL.SHALL;
        new HttpRequestImpl().getData(type, "1", new HttpResponse(this, type));
    }

    public void tabSz() {
        list.clear();
        trendRecyclerAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(true);
        type = MyURL.SZALL;
        new HttpRequestImpl().getData(type, "1", new HttpResponse(this, type));
    }

    public void tabHk() {
        list.clear();
        trendRecyclerAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(true);
        type = MyURL.HKALL;
        new HttpRequestImpl().getData(type, "1", new HttpResponse(this, type));
    }

    public void tabUs() {
        list.clear();
        trendRecyclerAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(true);
        type = MyURL.USALL;
        new HttpRequestImpl().getData(type, "1", new HttpResponse(this, type));
    }
    public void setRecyclerView(View view){
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        trendRecyclerAdapter=new TrendRecyclerAdapter(getActivity(), this.list);
        recyclerView.setAdapter(trendRecyclerAdapter);
        recyclerView.addOnScrollListener(new MyScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                new HttpRequestImpl().getData(type,String.valueOf(currentPage),new HttpResponse(Trend.this,type));
            }
        });
    }
    public interface Refresh{
        public void refresh();
    }
    Refresh refresh;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        refresh= (Refresh) activity;
    }
}
