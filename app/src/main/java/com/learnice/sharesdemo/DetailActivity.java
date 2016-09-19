package com.learnice.sharesdemo;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.learnice.sharesdemo.Adapter.SayAdapter;
import com.learnice.sharesdemo.Database.DbServices;
import com.learnice.sharesdemo.Http.HttpRequestImpl;
import com.learnice.sharesdemo.Http.HttpResponse;
import com.learnice.sharesdemo.Http.IDataResult;
import com.learnice.sharesdemo.Http.MyURL;
import com.learnice.sharesdemo.MyServerHttp.IMyServerDataResult;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpRequestImpl;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpResponseSay;
import com.learnice.sharesdemo.MyServerHttp.MyServerHttpResponseStatus;
import com.learnice.sharesdemo.Other.CurrentTime;
import com.learnice.sharesdemo.SharedData.AboutUser;
import com.learnice.sharesdemo.Stock.Say;
import com.learnice.sharesdemo.Stock.Shares;
import com.learnice.sharesdemo.Stock.Stock;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends BaseActivity implements IDataResult, IMyServerDataResult, View.OnClickListener {

    Stock stock;
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_stockName)
    TextView detailStockName;
    @BindView(R.id.detail_stockNum)
    TextView detailStockNum;
    @BindView(R.id.detail_nowPri)
    TextView detailNowPri;
    @BindView(R.id.detail_uppic)
    TextView detailUppic;
    @BindView(R.id.detail_limit)
    TextView detailLimit;
    @BindView(R.id.detail_formpri)
    TextView detailFormpri;
    @BindView(R.id.detail_openpri)
    TextView detailOpenpri;
    @BindView(R.id.detail_traAmount)
    TextView detailTraAmount;
    @BindView(R.id.detail_traNumber)
    TextView detailTraNumber;
    @BindView(R.id.detail_markValue)
    TextView detailMarkValue;
    @BindView(R.id.detail_date)
    TextView detailDate;
    @BindView(R.id.today_max)
    TextView todayMax;
    @BindView(R.id.today_min)
    TextView todayMin;
    @BindView(R.id.is_no_select_text)
    TextView isNoSelectText;
    @BindView(R.id.liner_4)
    LinearLayout liner4;
    @BindView(R.id.is_no_select_checkbox)
    CheckBox isNoSelectCheckbox;
    boolean select;
    List<Say> sayList;
    SayAdapter sayAdapter;
    @BindView(R.id.say_list)
    RecyclerView sayListDisPlay;
    @BindView(R.id.me_say_edit_text)
    EditText meSayEditText;
    @BindView(R.id.me_say_submit)
    Button meSaySubmit;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        stock=new Stock(null,null,null,null,null,null);
        setSupportActionBar(detailToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        actionBar.setTitle("");
        Intent intent = getIntent();
        String stockNum = intent.getStringExtra(SearchManager.QUERY);
        if (stockNum != null) {
            meSayEditText.setVisibility(View.GONE);
            meSaySubmit.setVisibility(View.GONE);
            liner4.setVisibility(View.GONE);
            setSearch(stockNum);
        } else {
            int type = intent.getIntExtra("type", 1);
            setDisplay(type);

        }
        liner4.setOnClickListener(this);
        meSaySubmit.setOnClickListener(this);
        sayList = new ArrayList<Say>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        sayListDisPlay.setLayoutManager(linearLayoutManager);
        sayAdapter=new SayAdapter(this,sayList);
        sayListDisPlay.setAdapter(sayAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setDisplay(int type) {
        switch (type) {
            case 1:
                stock = getIntent().getParcelableExtra("stock");
                setDisplayData(stock);
                break;
            case 2:
                stock = getIntent().getParcelableExtra("stock");
                new HttpRequestImpl().getData(stock.getStockType(), stock.getStockNum(), new HttpResponse(this, stock.getStockType()));
                Log.e("----------", stock.getStockType());
                break;
        }
    }

    public void setDisplayData(Stock stock) {
        setSayData();
        select = setSelectButton(stock);
        if (select) {
            isNoSelectCheckbox.setChecked(false);
            isNoSelectText.setText("添加");

        } else {
            isNoSelectCheckbox.setChecked(true);
            isNoSelectText.setText("删除");
        }

        String norPri = harlderData(stock.getNowPri());
        String uppIc = harlderData(stock.getUppic());
        String limit = harlderData(stock.getLimit());
        String maxPri = harlderData(stock.getMaxPri());
        String minPri = harlderData(stock.getMinPri());
        String formPri = harlderData(stock.getFormpri());
        String openPri = harlderData(stock.getOpenpri());
        if (Double.valueOf(limit) < 0) {
            detailLimit.setTextColor(Color.rgb(46, 125, 50));
            detailNowPri.setTextColor(Color.rgb(46, 125, 50));
            detailUppic.setTextColor(Color.rgb(46, 125, 50));
        } else {
            detailLimit.setTextColor(Color.rgb(213, 0, 0));
            detailNowPri.setTextColor(Color.rgb(213, 0, 0));
            detailUppic.setTextColor(Color.rgb(213, 0, 0));
        }
        double form = Double.valueOf(formPri);
        double open = Double.valueOf(openPri);
        if (form == open) {
            detailFormpri.setTextColor(Color.rgb(213, 0, 0));
            detailOpenpri.setTextColor(Color.rgb(213, 0, 0));
        } else if (form > open) {
            detailFormpri.setTextColor(Color.rgb(213, 0, 0));
            detailOpenpri.setTextColor(Color.rgb(46, 125, 50));
        } else if ((form < open)) {
            detailFormpri.setTextColor(Color.rgb(46, 125, 50));
            detailOpenpri.setTextColor(Color.rgb(213, 0, 0));
        }
        actionBar.setTitle(stock.getStockName()+"("+stock.getStockNum()+")");
        //detailStockName.setText(stock.getStockName()+"("+stock.getStockNum()+")");
        detailStockNum.setText(stock.getStockNum());
        detailNowPri.setText(norPri);
        detailUppic.setText(uppIc);
        detailLimit.setText(limit + "%");
        detailFormpri.setText(formPri);
        detailOpenpri.setText(openPri);
        detailTraAmount.setText(stock.getTraAmount());

        String number=String.valueOf(stock.getTraNumber());
        if (number.length()>4){
            number=number.substring(0,number.length()-4);
            detailTraNumber.setText(number+"万");
        }
        else {
            detailTraNumber.setText(number);
        }
        detailMarkValue.setText(stock.getMarkValue());
        detailDate.setText(stock.getDate());
        todayMin.setText(minPri);
        todayMax.setText(maxPri);
    }

    public void setSearch(String stockNum) {
        if (stockNum.length()>2) {
            String type = stockNum.substring(0, 2);
            if (type.equals("sh")) {
                new HttpRequestImpl().getData(MyURL.SHONE, stockNum, new HttpResponse(this, MyURL.SHONE));
            } else if (type.equals("sz")) {
                new HttpRequestImpl().getData(MyURL.SZONE, stockNum, new HttpResponse(this, MyURL.SZONE));
            } else if (type.equals("hk")) {
                new HttpRequestImpl().getData(MyURL.HKONE, stockNum, new HttpResponse(this, MyURL.HKONE));
            } else {
                new HttpRequestImpl().getData(MyURL.USONE, stockNum, new HttpResponse(this, MyURL.USONE));
            }
        }
        else {
            Snackbar.make(liner4,"股票代号输入有误",Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void resultObj(Object object) {

        if (object instanceof Stock){
            liner4.setVisibility(View.VISIBLE);
            meSaySubmit.setVisibility(View.VISIBLE);
            meSayEditText.setVisibility(View.VISIBLE);
            setDisplayData(this.stock=(Stock) object);
        }
        else {
            Snackbar.make(liner4,object.toString(),Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void resultList(List<?> list) {

    }

    @Override
    public void resultString(Object data) {
        //拿到点击select后的服务器反馈的状态
        select = !select;
        if (select) {
            isNoSelectCheckbox.setChecked(false);
            isNoSelectText.setText("添加");
        } else {
            isNoSelectCheckbox.setChecked(true);
            isNoSelectText.setText("删除");
        }
        Snackbar.make(detailToolbar, "操作成功", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void resultSayList(String list) {
        //拿到say数据并解析显示
        try {
            JSONArray jsonArray = new JSONArray(list);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                Say say = new Say(object.getString("userName"),
                        object.getString("sharesType"),
                        object.getString("sharesName"),
                        object.getString("content"),
                        object.getString("contentTime"));
                sayList.add(say);
            }
            sayAdapter=new SayAdapter(this, sayList);
            sayListDisPlay.setAdapter(sayAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public String harlderData(String data) {
        double value = Double.valueOf(data);
        BigDecimal bigDecimal = new BigDecimal(value);
        double valueNew = bigDecimal.setScale(2, BigDecimal.ROUND_CEILING).doubleValue();
        return String.valueOf(valueNew);
    }

    public boolean setSelectButton(Stock stock) {
        Cursor cursor = new DbServices(this).selectOne(new Shares(stock.getStockType(), stock.getStockNum()));
        if (cursor.getCount() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.liner_4:
                if (select) {
                    new DbServices(DetailActivity.this).add(new Shares(stock.getStockType(), stock.getStockNum()));
                    sendBroadcast(new Intent("insert"));
                    new MyServerHttpRequestImpl().getServerData("201",
                            new AboutUser(DetailActivity.this).readName(),
                            new Shares(stock.getStockType(), stock.getStockNum()),
                            new MyServerHttpResponseStatus(DetailActivity.this));

                } else {
                    new DbServices(DetailActivity.this).delete(new Shares(stock.getStockType(), stock.getStockNum()));
                    sendBroadcast(new Intent("insert"));
                    new MyServerHttpRequestImpl().getServerData("202", new AboutUser(DetailActivity.this).readName(),
                            new Shares(stock.getStockType(), stock.getStockNum()),
                            new MyServerHttpResponseStatus(DetailActivity.this));
                }
                break;
            case R.id.me_say_submit:
                String text = meSayEditText.getText().toString().trim();
                if (text.length() == 0) {
                    Toast.makeText(this, "请输入内容", Toast.LENGTH_SHORT).show();
                    Log.e("0000000000000000", CurrentTime.getCurrentTime());
                } else {
                    meSayEditText.setText("");
                    Say say=new Say(new AboutUser(this).readName(),
                            stock.getStockType(),
                            stock.getStockName(),
                            text,
                            CurrentTime.getCurrentTime());
                    new MyServerHttpRequestImpl().uploadSay("301",
                            say,
                            new MyServerHttpResponseStatus(this));
                    sayList.add(say);
                    sayAdapter.notifyItemInserted(sayList.size());
                    break;
                }
        }
    }

    public void setSayData() {
        new MyServerHttpRequestImpl().getServerData("300", new AboutUser(this).readName(),
                new Shares(stock.getStockType(), stock.getStockName()),
                new MyServerHttpResponseSay(this));

    }
}
