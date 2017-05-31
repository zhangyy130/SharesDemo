package com.learnice.sharesdemo.ui.details.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.learnice.base_library.base.BaseActivity;
import com.learnice.base_library.utils.HandleNumber;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.StandStockBean;
import com.learnice.sharesdemo.bean.tb_say;
import com.learnice.sharesdemo.ui.details.contract.DetailsContract;
import com.learnice.sharesdemo.ui.details.presenter.DetailsPresenter;
import com.learnice.sharesdemo.widget.adapter.SayAdapter;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;
import rx.functions.Func1;


public class DetailsActivity extends BaseActivity<DetailsPresenter> implements DetailsContract.View {

    ActionBar actionBar;
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_nowPri)
    TextView detailNowPri;
    @BindView(R.id.detail_uppic)
    TextView detailUppic;
    @BindView(R.id.detail_limit)
    TextView detailLimit;
    @BindView(R.id.detail_traNumber)
    TextView detailTraNumber;
    @BindView(R.id.today_max)
    TextView todayMax;
    @BindView(R.id.today_min)
    TextView todayMin;
    @BindView(R.id.detail_date)
    TextView detailDate;
    @BindView(R.id.detail_openpri)
    TextView detailOpenpri;
    @BindView(R.id.detail_formpri)
    TextView detailFormpri;
    @BindView(R.id.liner_4)
    RelativeLayout liner4;
    @BindView(R.id.pg_subscribe_load)
    ProgressBar pgSubscribeLoad;
    @BindView(R.id.detail_stockName)
    TextView detailStockName;
    @BindView(R.id.detail_stockNum)
    TextView detailStockNum;
    @BindView(R.id.detail_traAmount)
    TextView detailTraAmount;
    @BindView(R.id.detail_markValue)
    TextView detailMarkValue;
    @BindView(R.id.say_list)
    RecyclerView sayList;
    @BindView(R.id.me_say_edit_text)
    EditText meSayEditText;
    @BindView(R.id.me_say_submit)
    Button meSaySubmit;
    @BindView(R.id.is_no_select_checkbox)
    CheckBox isNoSelectCheckbox;
    @BindView(R.id.is_no_select_text)
    TextView isNoSelectText;
    //--------
    SayAdapter adapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initView() {
        Bundle bundle = getIntent().getBundleExtra("bundle");

        setSupportActionBar(detailToolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.abc_ic_ab_back_mtrl_am_alpha);
        actionBar.setTitle(bundle.getString("name") + "(" + bundle.getString("stock") + ")");

        mPresenter.getStockData(bundle.getString("type"), bundle.getString("stock").toLowerCase(), bundle.getString("name"));

        RxTextView.textChanges(meSayEditText).map(new Func1<CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence) {
                return charSequence.length() > 0;
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                RxView.enabled(meSaySubmit).call(aBoolean);
            }
        });

        RxView.clicks(meSaySubmit)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        String content = meSayEditText.getText().toString();
                        meSayEditText.setText("");
                        mPresenter.sendSay(content);
                    }
                });

        RxView.clicks(liner4)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.changeSubscribeStatus(isNoSelectCheckbox.isChecked());
                        pgSubscribeLoad.setVisibility(View.VISIBLE);
                        isNoSelectCheckbox.setVisibility(View.GONE);
                    }
                });

        adapter = new SayAdapter();
        sayList.setLayoutManager(new LinearLayoutManager(this));
        sayList.setAdapter(adapter);
        mPresenter.getSayData();
        mPresenter.getSubscribeStatus();
    }

    @Override
    public void initPresenter() {
        mPresenter = new DetailsPresenter(this);

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

    @Override
    public void setStockData(StandStockBean data) {
        //获取当前价
        double nowPri_ = HandleNumber.handleDoubleTwo(Double.valueOf(data.getNowPri()), 2);
        //涨跌幅
        double increase_ = HandleNumber.handleDoubleTwo(Double.valueOf(data.getIncrease()), 2);
        //涨跌幅
        double incerper_ = HandleNumber.handleDoubleTwo(Double.valueOf(data.getIncrePer()), 2);
        //最高价
        double todayMax_ = HandleNumber.handleDoubleTwo(Double.valueOf(data.getTodayMax()), 2);
        //最低价
        double todayMin_ = HandleNumber.handleDoubleTwo(Double.valueOf(data.getTodayMin()), 2);
        //今开
        double todayStartPro_ = HandleNumber.handleDoubleTwo(Double.valueOf(data.getTodayStartPri()), 2);
        //昨收
        double yestodEndPri_ = HandleNumber.handleDoubleTwo(Double.valueOf(data.getYestodEndPri()), 2);

        //-----------
        detailNowPri.setText(String.valueOf(nowPri_));
        detailUppic.setText(String.valueOf(increase_));
        detailLimit.setText(String.valueOf(incerper_) + "%");
        todayMax.setText(String.valueOf(todayMax_));
        todayMin.setText(String.valueOf(todayMin_));
        detailOpenpri.setText(String.valueOf(todayStartPro_));
        detailFormpri.setText(String.valueOf(yestodEndPri_));

        if (incerper_ >= 0) {
            detailNowPri.setTextColor(ContextCompat.getColor(this, R.color.up));
            detailUppic.setTextColor(ContextCompat.getColor(this, R.color.up));
            detailLimit.setTextColor(ContextCompat.getColor(this, R.color.up));
        } else {
            detailNowPri.setTextColor(ContextCompat.getColor(this, R.color.down));
            detailUppic.setTextColor(ContextCompat.getColor(this, R.color.down));
            detailLimit.setTextColor(ContextCompat.getColor(this, R.color.down));
        }
        //-------------
        if (todayStartPro_ > yestodEndPri_) {
            detailFormpri.setTextColor(ContextCompat.getColor(this, R.color.down));
        } else {
            detailOpenpri.setTextColor(ContextCompat.getColor(this, R.color.down));
        }
        //------
        String traNumber_ = data.getTraNumber();
        if (traNumber_.length() > 4) {
            traNumber_ = traNumber_.substring(0, traNumber_.length() - 4);
            detailTraNumber.setText(traNumber_ + "万");
        } else {
            detailTraNumber.setText(data.getTraNumber());
        }
        //-----------
        detailDate.setText(data.getDate());

    }

    @Override
    public void sendSayResult(String status) {
        ToastUtils.showShortToast(status);
    }

    @Override
    public void getSaydata(List<tb_say> tb_says) {
        adapter.addData(tb_says);
    }

    @Override
    public void getSubscribeStatus(Boolean aBoolean) {
        isNoSelectCheckbox.setChecked(aBoolean);
        pgSubscribeLoad.setVisibility(View.GONE);
        isNoSelectCheckbox.setVisibility(View.VISIBLE);
        if (aBoolean) {
            isNoSelectText.setText("删除");
        } else {
            isNoSelectText.setText("添加");
        }
    }

    @Override
    public void addSubscribeSuccess() {
        isNoSelectCheckbox.setChecked(true);
        isNoSelectText.setText("删除");
        pgSubscribeLoad.setVisibility(View.GONE);
        isNoSelectCheckbox.setVisibility(View.VISIBLE);
    }

    @Override
    public void DeleteSubscribeSuccess() {
        isNoSelectCheckbox.setChecked(false);
        isNoSelectText.setText("添加");
        pgSubscribeLoad.setVisibility(View.GONE);
        isNoSelectCheckbox.setVisibility(View.VISIBLE);
    }

    @Override
    public void sendSayResult(tb_say say) {
        adapter.addBean(say);
    }

}
