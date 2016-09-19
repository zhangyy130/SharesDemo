package com.learnice.sharesdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.learnice.sharesdemo.Adapter.HomeViewPagerAdapter;
import com.learnice.sharesdemo.SharedData.AboutFirst;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;

public class GuideActivity extends AppCompatActivity {

    @BindView(R.id.guide_view_pager)
    ViewPager guideViewPager;
    @BindView(R.id.guide_circle)
    CircleIndicator guideCircle;
    int[] ints = new int[]{R.drawable.say_view, R.drawable.trend_view, R.drawable.long_view};
    @BindView(R.id.guide_login)
    TextView guideLogin;
    @BindView(R.id.guide_tip_text)
    TextView guideTipText;
    @BindView(R.id.guide_image_left)
    ImageView guideImageLeft;
    @BindView(R.id.guide_image_right)
    ImageView guideImageRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        guideViewPager.setAdapter(new HomeViewPagerAdapter(this, ints));
        guideCircle.setViewPager(guideViewPager);
        guideViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                switch (position) {
                    case 0:
                        guideImageLeft.setVisibility(View.GONE);
                        guideImageRight.setVisibility(View.VISIBLE);
                        guideLogin.setVisibility(View.GONE);
                        guideTipText.setText("和所有的股友交流");
                        break;
                    case 1:
                        guideImageLeft.setVisibility(View.VISIBLE);
                        guideImageRight.setVisibility(View.VISIBLE);
                        guideLogin.setVisibility(View.GONE);
                        guideTipText.setText("查看所有股市行情");
                        break;
                    case 2:
                        guideTipText.setText("长按可添加、删除");
                        guideImageLeft.setVisibility(View.VISIBLE);
                        guideImageRight.setVisibility(View.GONE);
                        guideLogin.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.guide_login)
    public void onClick() {
        startActivity(new Intent(this, ConfirmPatternView.class));
        finish();
        AboutFirst.firstSave(this);
    }

    @OnClick({R.id.guide_image_left, R.id.guide_image_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.guide_image_left:
                guideViewPager.setCurrentItem(guideViewPager.getCurrentItem()-1);
                break;
            case R.id.guide_image_right:
                guideViewPager.setCurrentItem(guideViewPager.getCurrentItem()+1);
                break;
        }
    }
}
