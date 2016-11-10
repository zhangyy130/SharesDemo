package com.learnice.sharesdemo.Fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.learnice.sharesdemo.Adapter.SubscribeRecyclerViewAdapter;
import com.learnice.sharesdemo.Database.DbServices;
import com.learnice.sharesdemo.Database.SharesDataBase;
import com.learnice.sharesdemo.DetailActivity;
import com.learnice.sharesdemo.Http.HttpRequestImpl;
import com.learnice.sharesdemo.Http.HttpResponse;
import com.learnice.sharesdemo.Http.IDataResult;
import com.learnice.sharesdemo.MyServerHttp.IMyServerDataResult;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpRequestImpl;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpResponseStatus;
import com.learnice.sharesdemo.Other.RecyclerViewListener;
import com.learnice.sharesdemo.Other.RecyclerViewOnLongTimeListener;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.SharedData.AboutUser;
import com.learnice.sharesdemo.Stock.Shares;
import com.learnice.sharesdemo.Stock.Stock;

import java.util.ArrayList;
import java.util.List;

public class Subscribe extends Fragment implements IDataResult, SwipeRefreshLayout.OnRefreshListener, IMyServerDataResult {
    RecyclerView recyclerView;
    TextView textView;
    List<Stock> list;
    int subscribe;
    SwipeRefreshLayout swipeRefreshLayout;
    SubscribeRecyclerViewAdapter adapter;
    View rootView;
    TextView nozixuan;
    InsertDelete insertDelete;
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView==null){
            rootView = inflater.inflate(R.layout.fragment_subscribe, container, false);
            nozixuan= (TextView) rootView.findViewById(R.id.nozixuan);
            recyclerView = (RecyclerView) rootView.findViewById(R.id.subscribe_recyclerview);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.subscribe_swipeRefreshLayuot);
            swipeRefreshLayout.setOnRefreshListener(this);
            swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimaryDark,
                    R.color.colorAccent,
                    R.color.colorPrimary);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            list = new ArrayList<Stock>();
            subscribe = 0;
            adapter=new SubscribeRecyclerViewAdapter(getActivity(),list);
            recyclerView.setAdapter(adapter);
            //getActivity().startService(new Intent(getActivity(), SyncLocalDBServices.class));
            getDBdata();
            insertDelete=new InsertDelete();
            getActivity().registerReceiver(insertDelete,new IntentFilter("insert"));
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

    public void getDBdata() {
        Cursor cursor = new DbServices(getActivity()).select();
        subscribe = cursor.getCount();
        if (subscribe == 0) {
            nozixuan.setVisibility(View.VISIBLE);//根据数据库判断是否显示没有自选股
            swipeRefreshLayout.setRefreshing(false);
        } else {
            nozixuan.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                String type = cursor.getString(cursor.getColumnIndex(SharesDataBase.SHARES_TYPE));
                String name = cursor.getString(cursor.getColumnIndex(SharesDataBase.SHARES_NAME));
                new HttpRequestImpl().getData(type, name, new HttpResponse(this, type));
            }
        }
    }


    @Override
    public void resultObj(Object object) {
        list.add((Stock) object);
        adapter.notifyItemInserted(list.size());
        if (list.size() == subscribe) {
            swipeRefreshLayout.setRefreshing(false);
            //adapter = new SubscribeRecyclerViewAdapter(getActivity(), list);
           // recyclerView.setAdapter(adapter);
        }
        adapter.setOnItemListener(new RecyclerViewListener() {
            @Override
            public void onClick(int positon) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("type", 1);
                intent.putExtra("stock", list.get(positon));
                getActivity().startActivity(intent);
            }
        });
        adapter.setOnLongTimeListener(new RecyclerViewOnLongTimeListener() {
            @Override
            public void onClick(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("是否删除该自选股")
                        .setPositiveButton("好", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Stock stock = list.get(position);
                                int a = new DbServices(getActivity()).delete(new Shares(stock.getStockType(), stock.getStockNum()));
                                new MyServerHttpRequestImpl().getServerData("202", new AboutUser(getActivity()).readName(),
                                        new Shares(stock.getStockType(), stock.getStockNum()),
                                        new MyServerHttpResponseStatus(Subscribe.this));
                                list.remove(position);
                                if (list.size()==0){
                                    nozixuan.setVisibility(View.VISIBLE);
                                }
                                adapter.notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("不用了", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });

    }

    @Override
    public void resultList(List<?> list) {

    }

    @Override
    public void resultString(Object data) {
        if (data.toString().trim().equals("1")) {
//            list.clear();
//            getDBdata();
//            adapter.notifyDataSetChanged();
            Snackbar.make(swipeRefreshLayout, "删除成功", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void resultSayList(String list) {

    }

    @Override
    public void resultSayList(String list, int position) {

    }


    @Override
    public void onRefresh() {
        list.clear();
        getDBdata();
        adapter.notifyDataSetChanged();
    }
    public class InsertDelete extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            onRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(insertDelete);
    }
}
