package com.learnice.sharesdemo.ui.about.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.learnice.sharesdemo.R;
import com.learnice.sharesdemo.ui.news.NewsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AboutMeActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.txt_design)
    TextView txtDesign;
    @BindView(R.id.about_layout)
    RelativeLayout aboutLayout;
    @BindView(R.id.img_close)
    ImageView imgClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_me);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        aboutLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        txtDesign.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case MotionEvent.ACTION_UP:
                        txtDesign.setTextColor(getResources().getColor(R.color.black));
                        break;
                }
                return true;
            }
        });
    }

    @OnClick({R.id.img_close, R.id.txt_design})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_design:
                Intent intent = new Intent(this, NewsActivity.class);
                intent.putExtra("newsURL", "https://github.com/learnice");
                intent.putExtra("supportJs", true);
                startActivity(intent);
                break;
        }
    }
}
