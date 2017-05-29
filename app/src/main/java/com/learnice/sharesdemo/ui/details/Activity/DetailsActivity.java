package com.learnice.sharesdemo.ui.details.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.learnice.base_library.base.BaseActivity;
import com.learnice.sharesdemo.Adapter.SayAdapter;
import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.bean.StandStockBean;
import com.learnice.sharesdemo.bean.tb_say;
import com.learnice.sharesdemo.ui.details.contract.DetailsContract;
import com.learnice.sharesdemo.ui.details.presenter.DetailsPresenter;
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
    LinearLayout liner4;
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
        actionBar.setTitle(bundle.getString("name"));

        mPresenter.getStockData(bundle.getString("type"), bundle.getString("stock"), bundle.getString("name"));

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
                        mPresenter.sendSay(content);
                    }
                });

        RxView.clicks(liner4)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        mPresenter.changeSubscribeStatus(isNoSelectCheckbox.isChecked());
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
        detailNowPri.setText(data.getNowPri());
        detailUppic.setText(data.getIncrease());
        detailLimit.setText(data.getIncrePer());
        detailTraNumber.setText(data.getTraNumber());
        todayMax.setText(data.getTodayMax());
        todayMin.setText(data.getTodayMin());
        detailDate.setText(data.getDate());
        detailOpenpri.setText(data.getTodayStartPri());
        detailFormpri.setText(data.getYestodEndPri());
    }

    @Override
    public void sendSayResule(String status) {
        ToastUtils.showShortToast(status);
    }

    @Override
    public void getSaydata(List<tb_say> tb_says) {
        adapter.addData(tb_says);
    }

    @Override
    public void getSubscribeStatus(Boolean aBoolean) {
        isNoSelectCheckbox.setChecked(aBoolean);
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
    }

    @Override
    public void DeleteSubscribeSuccess() {
        isNoSelectCheckbox.setChecked(false);
        isNoSelectText.setText("添加");
    }

}
